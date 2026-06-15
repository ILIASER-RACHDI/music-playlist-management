package com.musicplaylist.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ArtistRequest(@NotBlank String name, String bio, Long userId) {}
