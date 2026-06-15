package com.musicplaylist.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SmartCreateRequest(@NotBlank String name, String description, @NotNull Long ownerId, List<Long> initialSongIds) {}
