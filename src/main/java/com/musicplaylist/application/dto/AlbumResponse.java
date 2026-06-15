package com.musicplaylist.application.dto;

import java.time.LocalDate;

public record AlbumResponse(Long id, String title, LocalDate releaseDate, Long artistId) {}
