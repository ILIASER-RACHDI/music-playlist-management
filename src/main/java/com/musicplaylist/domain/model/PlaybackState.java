package com.musicplaylist.domain.model;

public record PlaybackState(
        Long playlistId,
        Long currentSongId,
        PlaybackStatus status,
        int currentIndex
) {
    public static PlaybackState stopped() {
        return new PlaybackState(null, null, PlaybackStatus.STOPPED, -1);
    }
}
