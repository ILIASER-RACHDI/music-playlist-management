package com.musicplaylist.application.service;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.application.dto.PlaylistRequest;
import com.musicplaylist.application.dto.PlaylistResponse;
import com.musicplaylist.infrastructure.persistence.entity.PlaylistSongId;
import com.musicplaylist.infrastructure.persistence.repository.PlaylistRepository;
import com.musicplaylist.infrastructure.persistence.repository.PlaylistSongRepository;
import com.musicplaylist.infrastructure.persistence.repository.SongRepository;
import com.musicplaylist.infrastructure.persistence.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {
    @Mock PlaylistRepository playlistRepository;
    @Mock PlaylistSongRepository playlistSongRepository;
    @Mock SongRepository songRepository;
    @Mock UserRepository userRepository;
    @Mock MapperService mapper;
    @InjectMocks PlaylistService playlistService;

    @Test
    void create() {
        var playlist = TestFixtures.getMockedPlaylist();
        var response = TestFixtures.getMockedPlaylistResponse();
        when(userRepository.findById(1L)).thenReturn(Optional.of(TestFixtures.getMockedUser()));
        when(playlistRepository.save(any())).thenReturn(playlist);
        when(mapper.toPlaylistResponse(playlist)).thenReturn(response);
        var result = playlistService.create(TestFixtures.getMockedPlaylistRequest());
        assertThat(result.name()).isEqualTo("My Playlist");
    }

    @Test
    void findById() {
        var playlist = TestFixtures.getMockedPlaylist();
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(mapper.toPlaylistResponse(playlist)).thenReturn(TestFixtures.getMockedPlaylistResponse());
        var result = playlistService.findById(1L);
        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    void findByOwner() {
        var playlist = TestFixtures.getMockedPlaylist();
        when(playlistRepository.findByOwnerId(1L)).thenReturn(List.of(playlist));
        when(mapper.toPlaylistResponse(playlist)).thenReturn(TestFixtures.getMockedPlaylistResponse());
        var result = playlistService.findByOwner(1L);
        assertThat(result).hasSize(1);
    }

    @Test
    void update() {
        var playlist = TestFixtures.getMockedPlaylist();
        var response = new PlaylistResponse(1L, "Updated", "Updated description", 1L);
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(playlistRepository.save(playlist)).thenReturn(playlist);
        when(mapper.toPlaylistResponse(playlist)).thenReturn(response);
        var result = playlistService.update(1L, new PlaylistRequest("Updated", "Updated description", 1L));
        assertThat(result.name()).isEqualTo("Updated");
    }

    @Test
    void delete() {
        playlistService.delete(1L);
        verify(playlistRepository).deleteById(1L);
    }

    @Test
    void addSong() {
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(TestFixtures.getMockedPlaylist()));
        when(songRepository.findById(1L)).thenReturn(Optional.of(TestFixtures.getMockedSong()));
        when(playlistSongRepository.existsById(new PlaylistSongId(1L, 1L))).thenReturn(false);
        when(playlistSongRepository.countByPlaylistId(1L)).thenReturn(0);
        playlistService.addSong(1L, 1L);
        verify(playlistSongRepository).save(any());
    }

    @Nested
    class AddSongAlreadyExists {
        @Test
        void addSong() {
            when(playlistRepository.findById(1L)).thenReturn(Optional.of(TestFixtures.getMockedPlaylist()));
            when(songRepository.findById(1L)).thenReturn(Optional.of(TestFixtures.getMockedSong()));
            when(playlistSongRepository.existsById(new PlaylistSongId(1L, 1L))).thenReturn(true);
            playlistService.addSong(1L, 1L);
            verify(playlistSongRepository, never()).save(any());
        }
    }

    @Test
    void removeSong() {
        when(playlistSongRepository.findByPlaylistIdOrderByPositionAsc(1L)).thenReturn(List.of(TestFixtures.getMockedPlaylistSong()));
        playlistService.removeSong(1L, 1L);
        verify(playlistSongRepository).deleteByPlaylistIdAndSongId(1L, 1L);
        verify(playlistSongRepository).saveAll(any());
    }

    @Test
    void reorder() {
        var first = TestFixtures.getMockedPlaylistSong();
        var second = TestFixtures.getMockedSecondPlaylistSong();
        when(playlistSongRepository.findByPlaylistIdOrderByPositionAsc(1L)).thenReturn(List.of(first, second));
        playlistService.reorder(1L, TestFixtures.getMockedReorderRequest());
        assertThat(first.getPosition()).isEqualTo(1);
        assertThat(second.getPosition()).isEqualTo(0);
        verify(playlistSongRepository).saveAll(any());
    }

    @Test
    void getSongs() {
        var playlistSong = TestFixtures.getMockedPlaylistSong();
        when(playlistSongRepository.findByPlaylistIdOrderByPositionAsc(1L)).thenReturn(List.of(playlistSong));
        when(mapper.toDomainSong(playlistSong.getSong())).thenReturn(TestFixtures.getMockedDomainSong());
        var result = playlistService.getSongs(1L);
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().title()).isEqualTo("One More Time");
    }

    @Test
    void replaceOrder() {
        var first = TestFixtures.getMockedPlaylistSong();
        var second = TestFixtures.getMockedSecondPlaylistSong();
        when(playlistSongRepository.findByPlaylistIdOrderByPositionAsc(1L)).thenReturn(List.of(first, second));
        playlistService.replaceOrder(1L, List.of(TestFixtures.getMockedSecondDomainSong(), TestFixtures.getMockedDomainSong()));
        assertThat(first.getPosition()).isEqualTo(1);
        assertThat(second.getPosition()).isEqualTo(0);
        verify(playlistSongRepository).saveAll(any());
    }

    @Test
    void getPlaylist() {
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(TestFixtures.getMockedPlaylist()));
        var result = playlistService.getPlaylist(1L);
        assertThat(result.getId()).isEqualTo(1L);
    }
}
