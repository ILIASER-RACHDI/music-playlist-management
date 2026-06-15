package com.musicplaylist;

import com.musicplaylist.application.dto.AlbumRequest;
import com.musicplaylist.application.dto.ArtistRequest;
import com.musicplaylist.application.dto.PlaylistRequest;
import com.musicplaylist.application.dto.PlaylistResponse;
import com.musicplaylist.application.dto.ReorderRequest;
import com.musicplaylist.application.dto.SmartCreateRequest;
import com.musicplaylist.application.dto.SongRequest;
import com.musicplaylist.application.dto.SongResponse;
import com.musicplaylist.domain.model.Role;
import com.musicplaylist.domain.model.Song;
import com.musicplaylist.infrastructure.persistence.entity.AlbumEntity;
import com.musicplaylist.infrastructure.persistence.entity.ArtistEntity;
import com.musicplaylist.infrastructure.persistence.entity.PlaylistEntity;
import com.musicplaylist.infrastructure.persistence.entity.PlaylistSongEntity;
import com.musicplaylist.infrastructure.persistence.entity.PlaylistSongId;
import com.musicplaylist.infrastructure.persistence.entity.SongEntity;
import com.musicplaylist.infrastructure.persistence.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

public final class TestFixtures {
    private TestFixtures() {}

    public static UserEntity getMockedUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("user@test.com");
        user.setPassword("encoded-password");
        user.setRole(Role.ROLE_USER);
        return user;
    }

    public static UserEntity getMockedArtistUser() {
        UserEntity user = new UserEntity();
        user.setId(2L);
        user.setEmail("artist@test.com");
        user.setPassword("encoded-password");
        user.setRole(Role.ROLE_ARTIST);
        return user;
    }

    public static ArtistEntity getMockedArtist() {
        ArtistEntity artist = new ArtistEntity();
        artist.setId(1L);
        artist.setName("Daft Punk");
        artist.setBio("Electronic music duo");
        artist.setUser(getMockedArtistUser());
        return artist;
    }

    public static AlbumEntity getMockedAlbum() {
        AlbumEntity album = new AlbumEntity();
        album.setId(1L);
        album.setTitle("Discovery");
        album.setReleaseDate(LocalDate.of(2001, 3, 12));
        album.setArtist(getMockedArtist());
        return album;
    }

    public static SongEntity getMockedSong() {
        SongEntity song = new SongEntity();
        song.setId(1L);
        song.setTitle("One More Time");
        song.setGenre("ELECTRO");
        song.setDuration(320);
        song.setAudioUrl("https://cdn.example.com/one-more-time.mp3");
        song.setAlbum(getMockedAlbum());
        song.setArtist(getMockedArtist());
        return song;
    }

    public static SongEntity getMockedSecondSong() {
        SongEntity song = new SongEntity();
        song.setId(2L);
        song.setTitle("Digital Love");
        song.setGenre("ELECTRO");
        song.setDuration(301);
        song.setAudioUrl("https://cdn.example.com/digital-love.mp3");
        song.setAlbum(getMockedAlbum());
        song.setArtist(getMockedArtist());
        return song;
    }

    public static PlaylistEntity getMockedPlaylist() {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1L);
        playlist.setName("My Playlist");
        playlist.setDescription("My description");
        playlist.setOwner(getMockedUser());
        return playlist;
    }

    public static PlaylistSongEntity getMockedPlaylistSong() {
        PlaylistSongEntity playlistSong = new PlaylistSongEntity();
        playlistSong.setId(new PlaylistSongId(1L, 1L));
        playlistSong.setPlaylist(getMockedPlaylist());
        playlistSong.setSong(getMockedSong());
        playlistSong.setPosition(0);
        return playlistSong;
    }

    public static PlaylistSongEntity getMockedSecondPlaylistSong() {
        PlaylistSongEntity playlistSong = new PlaylistSongEntity();
        playlistSong.setId(new PlaylistSongId(1L, 2L));
        playlistSong.setPlaylist(getMockedPlaylist());
        playlistSong.setSong(getMockedSecondSong());
        playlistSong.setPosition(1);
        return playlistSong;
    }

    public static Song getMockedDomainSong() {
        return new Song(1L, "One More Time", "Daft Punk", "Discovery", "ELECTRO", 320, "https://cdn.example.com/one-more-time.mp3");
    }

    public static Song getMockedSecondDomainSong() {
        return new Song(2L, "Digital Love", "Daft Punk", "Discovery", "ELECTRO", 301, "https://cdn.example.com/digital-love.mp3");
    }

    public static Song getMockedRockDomainSong() {
        return new Song(3L, "Rock Song", "Other Artist", "Other Album", "ROCK", 240, "https://cdn.example.com/rock.mp3");
    }

    public static List<Song> getMockedDomainSongs() {
        return List.of(getMockedDomainSong(), getMockedRockDomainSong(), getMockedSecondDomainSong());
    }

    public static ArtistRequest getMockedArtistRequest() {
        return new ArtistRequest("Daft Punk", "Electronic music duo", 2L);
    }

    public static AlbumRequest getMockedAlbumRequest() {
        return new AlbumRequest("Discovery", LocalDate.of(2001, 3, 12));
    }

    public static SongRequest getMockedSongRequest() {
        return new SongRequest("One More Time", "ELECTRO", 320, "https://cdn.example.com/one-more-time.mp3");
    }

    public static PlaylistRequest getMockedPlaylistRequest() {
        return new PlaylistRequest("My Playlist", "My description", 1L);
    }

    public static ReorderRequest getMockedReorderRequest() {
        return new ReorderRequest(List.of(2L, 1L));
    }

    public static SmartCreateRequest getMockedSmartCreateRequest() {
        return new SmartCreateRequest("Smart Playlist", "Created smartly", 1L, List.of(1L, 2L));
    }

    public static SongResponse getMockedSongResponse() {
        return new SongResponse(1L, "One More Time", "Daft Punk", "Discovery", "ELECTRO", 320, "https://cdn.example.com/one-more-time.mp3");
    }

    public static PlaylistResponse getMockedPlaylistResponse() {
        return new PlaylistResponse(1L, "My Playlist", "My description", 1L);
    }
}
