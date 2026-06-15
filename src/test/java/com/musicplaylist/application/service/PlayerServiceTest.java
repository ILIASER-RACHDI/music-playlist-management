package com.musicplaylist.application.service;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.domain.model.PlaybackStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
    @Mock PlaylistService playlistService;

    @Test
    void play() {
        when(playlistService.getSongs(1L)).thenReturn(TestFixtures.getMockedDomainSongs());
        var playerService = new PlayerService(playlistService);
        var result = playerService.play(1L);
        assertThat(result.status()).isEqualTo(PlaybackStatus.PLAYING);
        assertThat(result.currentSongId()).isEqualTo(1L);
    }

    @org.junit.jupiter.api.Nested
    class PlayEmptyPlaylist {
        @Test
        void play() {
            when(playlistService.getSongs(1L)).thenReturn(List.of());
            var playerService = new PlayerService(playlistService);
            assertThatThrownBy(() -> playerService.play(1L)).isInstanceOf(IllegalStateException.class);
        }
    }

    @Test
    void pause() {
        when(playlistService.getSongs(1L)).thenReturn(TestFixtures.getMockedDomainSongs());
        var playerService = new PlayerService(playlistService);
        playerService.play(1L);
        var result = playerService.pause();
        assertThat(result.status()).isEqualTo(PlaybackStatus.PAUSED);
    }

    @Test
    void next() {
        when(playlistService.getSongs(1L)).thenReturn(TestFixtures.getMockedDomainSongs());
        var playerService = new PlayerService(playlistService);
        playerService.play(1L);
        var result = playerService.next();
        assertThat(result.currentIndex()).isEqualTo(1);
    }

    @org.junit.jupiter.api.Nested
    class NextStopped {
        @Test
        void next() {
            var playerService = new PlayerService(playlistService);
            var result = playerService.next();
            assertThat(result.status()).isEqualTo(PlaybackStatus.STOPPED);
        }
    }

    @Test
    void previous() {
        when(playlistService.getSongs(1L)).thenReturn(TestFixtures.getMockedDomainSongs());
        var playerService = new PlayerService(playlistService);
        playerService.play(1L);
        playerService.next();
        var result = playerService.previous();
        assertThat(result.currentIndex()).isEqualTo(0);
    }

    @org.junit.jupiter.api.Nested
    class PreviousStopped {
        @Test
        void previous() {
            var playerService = new PlayerService(playlistService);
            var result = playerService.previous();
            assertThat(result.status()).isEqualTo(PlaybackStatus.STOPPED);
        }
    }

    @Test
    void state() {
        var playerService = new PlayerService(playlistService);
        var result = playerService.state();
        assertThat(result.status()).isEqualTo(PlaybackStatus.STOPPED);
    }
}
