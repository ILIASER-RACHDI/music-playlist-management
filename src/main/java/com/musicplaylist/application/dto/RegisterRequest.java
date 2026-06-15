package com.musicplaylist.application.dto;

import com.musicplaylist.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(@Email String email, @NotBlank String password, @NotNull Role role) {}
