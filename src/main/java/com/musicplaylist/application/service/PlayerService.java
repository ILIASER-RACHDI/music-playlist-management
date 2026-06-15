package com.musicplaylist.application.service;

import com.musicplaylist.domain.model.PlaybackState;
import com.musicplaylist.domain.model.PlaybackStatus;
import com.musicplaylist.domain.model.Song;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class PlayerService {
    private final PlaylistService playlistService;
    private final ReentrantLock lock = new ReentrantLock();
    private PlaybackState state = PlaybackState.stopped();

    public PlayerService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    public PlaybackState play(Long playlistId) {
        lock.lock();
        try {
            List<Song> songs = playlistService.getSongs(playlistId);
            if (songs.isEmpty()) {
                throw new IllegalStateException("Cannot play an empty playlist");
            }
            state = new PlaybackState(playlistId, songs.get(0).id(), PlaybackStatus.PLAYING, 0);
            return state;
        } finally {
            lock.unlock();
        }
    }

    public PlaybackState pause() {
        lock.lock();
        try {
            state = new PlaybackState(state.playlistId(), state.currentSongId(), PlaybackStatus.PAUSED, state.currentIndex());
            return state;
        } finally {
            lock.unlock();
        }
    }

    public PlaybackState next() {
        lock.lock();
        try {
            if (state.playlistId() == null) {
                return state;
            }
            List<Song> songs = playlistService.getSongs(state.playlistId());
            int nextIndex = Math.min(state.currentIndex() + 1, songs.size() - 1);
            state = new PlaybackState(state.playlistId(), songs.get(nextIndex).id(), PlaybackStatus.PLAYING, nextIndex);
            return state;
        } finally {
            lock.unlock();
        }
    }

    public PlaybackState previous() {
        lock.lock();
        try {
            if (state.playlistId() == null) {
                return state;
            }
            List<Song> songs = playlistService.getSongs(state.playlistId());
            int previousIndex = Math.max(state.currentIndex() - 1, 0);
            state = new PlaybackState(state.playlistId(), songs.get(previousIndex).id(), PlaybackStatus.PLAYING, previousIndex);
            return state;
        } finally {
            lock.unlock();
        }
    }

    public PlaybackState state() {
        lock.lock();
        try {
            return state;
        } finally {
            lock.unlock();
        }
    }
}
