package com.musicplaylist.application.service;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.domain.model.PlaybackState;
import com.musicplaylist.domain.model.PlaybackStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaylistOrchestrationServiceTest {
    @Mock PlaylistService playlistService;
    @Mock RecommendationService recommendationService;
    @Mock PlayerService playerService;
    @InjectMocks PlaylistOrchestrationService playlistOrchestrationService;

    @Test
    void smartCreate() {
        var request = TestFixtures.getMockedSmartCreateRequest();
        var playlist = TestFixtures.getMockedPlaylistResponse();
        var state = new PlaybackState(1L, 1L, PlaybackStatus.PLAYING, 0);
        when(playlistService.create(org.mockito.ArgumentMatchers.any())).thenReturn(playlist);
        when(recommendationService.recommend(1L)).thenReturn(List.of(TestFixtures.getMockedSongResponse()));
        when(playerService.play(1L)).thenReturn(state);
        var result = playlistOrchestrationService.smartCreate(request);
        assertThat(result).containsKeys("playlist", "recommendations", "player");
        verify(playlistService).addSong(1L, 1L);
        verify(playlistService).addSong(1L, 2L);
    }
}
