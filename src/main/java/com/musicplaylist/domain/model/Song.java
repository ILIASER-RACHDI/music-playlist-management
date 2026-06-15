package com.musicplaylist.domain.model;

public record Song(
        Long id,
        String title,
        String artistName,
        String albumTitle,
        String genre,
        Integer duration,
        String audioUrl
) {
}
