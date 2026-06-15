package com.musicplaylist.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SongRequest(@NotBlank String title, @NotBlank String genre, @NotNull Integer duration, String audioUrl) {}
