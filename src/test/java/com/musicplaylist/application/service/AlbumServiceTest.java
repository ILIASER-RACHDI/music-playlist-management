package com.musicplaylist.application.service;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.application.dto.AlbumResponse;
import com.musicplaylist.infrastructure.persistence.repository.AlbumRepository;
import com.musicplaylist.infrastructure.persistence.repository.ArtistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {
    @Mock AlbumRepository albumRepository;
    @Mock ArtistRepository artistRepository;
    @Mock MapperService mapper;
    @InjectMocks AlbumService albumService;

    @Test
    void create() {
        var artist = TestFixtures.getMockedArtist();
        var album = TestFixtures.getMockedAlbum();
        var response = new AlbumResponse(1L, "Discovery", java.time.LocalDate.of(2001, 3, 12), 1L);
        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));
        when(albumRepository.save(any())).thenReturn(album);
        when(mapper.toAlbumResponse(album)).thenReturn(response);
        var result = albumService.create(1L, TestFixtures.getMockedAlbumRequest());
        assertThat(result.title()).isEqualTo("Discovery");
    }

    @Test
    void findByArtist() {
        var album = TestFixtures.getMockedAlbum();
        var response = new AlbumResponse(1L, "Discovery", java.time.LocalDate.of(2001, 3, 12), 1L);
        when(albumRepository.findByArtistId(1L)).thenReturn(List.of(album));
        when(mapper.toAlbumResponse(album)).thenReturn(response);
        var result = albumService.findByArtist(1L);
        assertThat(result).hasSize(1);
    }
}
