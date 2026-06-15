package com.musicplaylist.application.service;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.infrastructure.persistence.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {
    @Mock PlaylistService playlistService;
    @Mock SongRepository songRepository;
    @Mock MapperService mapper;
    @InjectMocks RecommendationService recommendationService;

    @Test
    void recommend() {
        var song = TestFixtures.getMockedSecondSong();
        when(playlistService.getSongs(1L)).thenReturn(List.of(TestFixtures.getMockedDomainSong()));
        when(songRepository.findTop10ByGenreInAndIdNotIn(anyCollection(), anyCollection())).thenReturn(List.of(song));
        when(mapper.toSongResponse(song)).thenReturn(TestFixtures.getMockedSongResponse());
        var result = recommendationService.recommend(1L);
        assertThat(result).hasSize(1);
    }

    @org.junit.jupiter.api.Nested
    class RecommendEmptyGenres {
        @Test
        void recommend() {
            var song = TestFixtures.getMockedSong();
            when(playlistService.getSongs(1L)).thenReturn(List.of());
            when(songRepository.findAll()).thenReturn(List.of(song));
            when(mapper.toSongResponse(song)).thenReturn(TestFixtures.getMockedSongResponse());
            var result = recommendationService.recommend(1L);
            assertThat(result).hasSize(1);
        }
    }
}
