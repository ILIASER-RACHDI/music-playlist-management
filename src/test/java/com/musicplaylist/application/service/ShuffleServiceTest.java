package com.musicplaylist.application.service;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.application.dto.SongResponse;
import com.musicplaylist.domain.model.ShuffleType;
import com.musicplaylist.domain.model.Song;
import com.musicplaylist.domain.strategy.RandomShuffleStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShuffleServiceTest {
    @Mock PlaylistService playlistService;
    @Mock MapperService mapper;

    @Test
    void shuffle() {
        var shuffleService = new ShuffleService(playlistService, mapper, List.of(new RandomShuffleStrategy()));
        when(playlistService.getSongs(1L)).thenReturn(TestFixtures.getMockedDomainSongs());
        when(mapper.toSongResponse(any(Song.class))).thenAnswer(invocation -> {
            var song = invocation.getArgument(0, Song.class);
            return new SongResponse(song.id(), song.title(), song.artistName(), song.albumTitle(), song.genre(), song.duration(), song.audioUrl());
        });
        var result = shuffleService.shuffle(1L, ShuffleType.RANDOM);
        assertThat(result).hasSize(3);
        verify(playlistService).replaceOrder(any(), any());
    }

    @org.junit.jupiter.api.Nested
    class ShuffleUnsupported {
        @Test
        void shuffle() {
            var shuffleService = new ShuffleService(playlistService, mapper, List.of());
            assertThatThrownBy(() -> shuffleService.shuffle(1L, ShuffleType.RANDOM)).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
