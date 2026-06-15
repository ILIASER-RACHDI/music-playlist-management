package com.musicplaylist.application.service;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.application.dto.ArtistResponse;
import com.musicplaylist.infrastructure.persistence.repository.ArtistRepository;
import com.musicplaylist.infrastructure.persistence.repository.UserRepository;
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
class ArtistServiceTest {
    @Mock ArtistRepository artistRepository;
    @Mock UserRepository userRepository;
    @Mock MapperService mapper;
    @InjectMocks ArtistService artistService;

    @Test
    void create() {
        var request = TestFixtures.getMockedArtistRequest();
        var artist = TestFixtures.getMockedArtist();
        var response = new ArtistResponse(1L, "Daft Punk", "Electronic music duo");
        when(userRepository.findById(2L)).thenReturn(Optional.of(TestFixtures.getMockedArtistUser()));
        when(artistRepository.save(any())).thenReturn(artist);
        when(mapper.toArtistResponse(artist)).thenReturn(response);
        var result = artistService.create(request);
        assertThat(result.name()).isEqualTo("Daft Punk");
    }

    @Test
    void findAll() {
        var artist = TestFixtures.getMockedArtist();
        var response = new ArtistResponse(1L, "Daft Punk", "Electronic music duo");
        when(artistRepository.findAll()).thenReturn(List.of(artist));
        when(mapper.toArtistResponse(artist)).thenReturn(response);
        var result = artistService.findAll();
        assertThat(result).hasSize(1);
    }
}
