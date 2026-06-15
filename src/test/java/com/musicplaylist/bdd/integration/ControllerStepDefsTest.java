package com.musicplaylist.bdd.integration;

import com.musicplaylist.domain.model.Role;
import com.musicplaylist.infrastructure.persistence.entity.AlbumEntity;
import com.musicplaylist.infrastructure.persistence.entity.ArtistEntity;
import com.musicplaylist.infrastructure.persistence.entity.PlaylistEntity;
import com.musicplaylist.infrastructure.persistence.entity.PlaylistSongEntity;
import com.musicplaylist.infrastructure.persistence.entity.SongEntity;
import com.musicplaylist.infrastructure.persistence.entity.UserEntity;
import com.musicplaylist.infrastructure.persistence.repository.AlbumRepository;
import com.musicplaylist.infrastructure.persistence.repository.ArtistRepository;
import com.musicplaylist.infrastructure.persistence.repository.PlaylistRepository;
import com.musicplaylist.infrastructure.persistence.repository.PlaylistSongRepository;
import com.musicplaylist.infrastructure.persistence.repository.SongRepository;
import com.musicplaylist.infrastructure.persistence.repository.UserRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControllerStepDefsTest extends ControllerIntegrationTest {


    private final RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<String> response;
    private String token;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistSongRepository playlistSongRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Given("the following users are in database")
    public void theFollowingUsersAreInDatabase(DataTable dataTable) {
        userRepository.deleteAll();

        List<Map<String, String>> rows = dataTable.asMaps();

        for (Map<String, String> row : rows) {
            UserEntity user = new UserEntity();
            user.setId(Long.valueOf(row.get("id")));
            user.setEmail(row.get("email"));
            user.setPassword(passwordEncoder.encode(row.get("password")));
            user.setRole(Role.valueOf(row.get("role")));
            userRepository.save(user);
        }
    }

    @Given("the following artists are in database")
    public void theFollowingArtistsAreInDatabase(DataTable dataTable) {
        artistRepository.deleteAll();

        for (Map<String, String> row : dataTable.asMaps()) {
            UserEntity user = userRepository.findById(Long.valueOf(row.get("user_id")))
                    .orElseThrow();

            ArtistEntity artist = new ArtistEntity();
            artist.setId(Long.valueOf(row.get("id")));
            artist.setName(row.get("name"));
            artist.setBio(row.get("bio"));
            artist.setUser(user);

            artistRepository.save(artist);
        }
    }

    @Given("the following albums are in database")
    public void theFollowingAlbumsAreInDatabase(DataTable dataTable) {
        albumRepository.deleteAll();

        for (Map<String, String> row : dataTable.asMaps()) {
            ArtistEntity artist = artistRepository.findById(Long.valueOf(row.get("artist_id")))
                    .orElseThrow();

            AlbumEntity album = new AlbumEntity();
            album.setId(Long.valueOf(row.get("id")));
            album.setTitle(row.get("title"));
            album.setArtist(artist);
            album.setReleaseDate(LocalDate.parse(row.get("release_date")));

            albumRepository.save(album);
        }
    }

    @Given("the following songs are in database")
    public void theFollowingSongsAreInDatabase(DataTable dataTable) {
        songRepository.deleteAll();

        for (Map<String, String> row : dataTable.asMaps()) {
            AlbumEntity album = albumRepository.findById(Long.valueOf(row.get("album_id")))
                    .orElse(null);

            ArtistEntity artist = artistRepository.findById(Long.valueOf(row.get("artist_id")))
                    .orElse(null);

            SongEntity song = new SongEntity();
            song.setId(Long.valueOf(row.get("id")));
            song.setTitle(row.get("title"));
            song.setGenre(row.get("genre"));
            song.setDuration(Integer.valueOf(row.get("duration")));
            song.setAudioUrl(row.get("audio_url"));
            song.setAlbum(album);
            song.setArtist(artist);

            songRepository.save(song);
        }
    }

    @Given("the following playlists are in database")
    public void theFollowingPlaylistsAreInDatabase(DataTable dataTable) {
        playlistRepository.deleteAll();

        for (Map<String, String> row : dataTable.asMaps()) {
            UserEntity owner = userRepository.findById(Long.valueOf(row.get("owner_id")))
                    .orElseThrow();

            PlaylistEntity playlist = new PlaylistEntity();
            playlist.setId(Long.valueOf(row.get("id")));
            playlist.setName(row.get("name"));
            playlist.setDescription(row.get("description"));
            playlist.setOwner(owner);

            playlistRepository.save(playlist);
        }
    }

    @Given("the following playlist songs are in database")
    public void theFollowingPlaylistSongsAreInDatabase(DataTable dataTable) {
        playlistSongRepository.deleteAll();

        for (Map<String, String> row : dataTable.asMaps()) {
            PlaylistEntity playlist = playlistRepository.findById(Long.valueOf(row.get("playlist_id")))
                    .orElseThrow();

            SongEntity song = songRepository.findById(Long.valueOf(row.get("song_id")))
                    .orElseThrow();

            PlaylistSongEntity playlistSong = new PlaylistSongEntity();
            playlistSong.setPlaylist(playlist);
            playlistSong.setSong(song);
            playlistSong.setPosition(Integer.valueOf(row.get("position")));

            playlistSongRepository.save(playlistSong);
        }
    }

    @Given("the authenticated user is")
    public void theAuthenticatedUserIs(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().get(0);

        String body = """
                {
                  "email": "%s",
                  "password": "password"
                }
                """.formatted(row.get("email"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        response = restTemplate.postForEntity(
                url("/auth/login"),
                new HttpEntity<>(body, headers),
                String.class
        );

        token = extractToken(response.getBody());
    }

    @Given("the player is playing playlist {long}")
    public void thePlayerIsPlayingPlaylist(Long playlistId) {
        executeRequest(HttpMethod.POST, "/player/play/" + playlistId, null);
    }

    @When("^HTTP request GET (.+)$")
    public void httpRequestGet(String path) {
        executeRequest(HttpMethod.GET, path, null);
    }

    //@When("^HTTP request POST (.+)$")
    /*public void httpRequestPost(String path) {
        executeRequest(HttpMethod.POST, path, null);
    }*/

    @When("^HTTP request DELETE (.+)$")
    public void httpRequestDelete(String path) {
        executeRequest(HttpMethod.DELETE, path, null);
    }

    @When("^HTTP request POST (.+) with body$")
    public void httpRequestPostWithBody(String path, String body) {
        executeRequest(HttpMethod.POST, path, body);
    }

    @When("^HTTP request PUT (.+) with body$")
    public void httpRequestPutWithBody(String path, String body) {
        executeRequest(HttpMethod.PUT, path, body);
    }

    @Then("HTTP response with status {int} and body contains")
    public void httpResponseWithStatusAndBodyContains(int status, String expectedBody) {
        assertEquals(status, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains(expectedBody.trim()));
    }

    @Then("HTTP response with status {int} and body does not contain")
    public void httpResponseWithStatusAndBodyDoesNotContain(int status, String expectedBody) {
        assertEquals(status, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().contains(expectedBody.trim()));
    }

    private void executeRequest(HttpMethod method, String path, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (token != null) {
            headers.setBearerAuth(token);
        }

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        response = restTemplate.exchange(
                url(path),
                method,
                request,
                String.class
        );
    }

    private String extractToken(String body) {
        if (body == null) {
            return null;
        }

        return body
                .replace("{", "")
                .replace("}", "")
                .replace("\"", "")
                .replace("token:", "")
                .trim();
    }
}