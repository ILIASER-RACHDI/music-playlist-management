package com.musicplaylist.application.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record AlbumRequest(@NotBlank String title, LocalDate releaseDate) {}
