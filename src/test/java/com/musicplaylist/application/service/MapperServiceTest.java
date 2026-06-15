package com.musicplaylist.application.service;

import com.musicplaylist.TestFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MapperServiceTest {
    private final MapperService mapperService = new MapperService();

    @Test
    void toArtistResponse() {
        var response = mapperService.toArtistResponse(TestFixtures.getMockedArtist());
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Daft Punk");
    }

    @Test
    void toAlbumResponse() {
        var response = mapperService.toAlbumResponse(TestFixtures.getMockedAlbum());
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.artistId()).isEqualTo(1L);
    }

    @Test
    void toDomainSong() {
        var response = mapperService.toDomainSong(TestFixtures.getMockedSong());
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.artistName()).isEqualTo("Daft Punk");
        assertThat(response.albumTitle()).isEqualTo("Discovery");
    }

    @Test
    void toSongResponse() {
        var response = mapperService.toSongResponse(TestFixtures.getMockedSong());
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("One More Time");
    }

    @Test
    void toPlaylistResponse() {
        var response = mapperService.toPlaylistResponse(TestFixtures.getMockedPlaylist());
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.ownerId()).isEqualTo(1L);
    }
}
