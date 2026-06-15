package com.musicplaylist.application.dto;

public record SongResponse(Long id, String title, String artistName, String albumTitle, String genre, Integer duration, String audioUrl) {}
