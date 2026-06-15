package com.musicplaylist.application.service;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.infrastructure.persistence.repository.AlbumRepository;
import com.musicplaylist.infrastructure.persistence.repository.SongRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {
    @Mock SongRepository songRepository;
    @Mock AlbumRepository albumRepository;
    @Mock MapperService mapper;
    @InjectMocks SongService songService;

    @Test
    void create() {
        var album = TestFixtures.getMockedAlbum();
        var song = TestFixtures.getMockedSong();
        var response = TestFixtures.getMockedSongResponse();
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(songRepository.save(any())).thenReturn(song);
        when(mapper.toSongResponse(song)).thenReturn(response);
        var result = songService.create(1L, 1L, TestFixtures.getMockedSongRequest());
        assertThat(result.title()).isEqualTo("One More Time");
    }

    @Nested
    class CreateAlbumDoesNotBelongToArtist {
        @Test
        void create() {
            when(albumRepository.findById(1L)).thenReturn(Optional.of(TestFixtures.getMockedAlbum()));
            assertThatThrownBy(() -> songService.create(99L, 1L, TestFixtures.getMockedSongRequest()))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void findByAlbum() {
        var song = TestFixtures.getMockedSong();
        when(songRepository.findByAlbumId(1L)).thenReturn(List.of(song));
        when(mapper.toSongResponse(song)).thenReturn(TestFixtures.getMockedSongResponse());
        var result = songService.findByAlbum(1L);
        assertThat(result).hasSize(1);
    }

    @Test
    void findAll() {
        var song = TestFixtures.getMockedSong();
        when(songRepository.findAll()).thenReturn(List.of(song));
        when(mapper.toSongResponse(song)).thenReturn(TestFixtures.getMockedSongResponse());
        var result = songService.findAll();
        assertThat(result).hasSize(1);
    }
}
