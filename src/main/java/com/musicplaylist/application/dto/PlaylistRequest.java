package com.musicplaylist.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlaylistRequest(@NotBlank String name, String description, @NotNull Long ownerId) {}
