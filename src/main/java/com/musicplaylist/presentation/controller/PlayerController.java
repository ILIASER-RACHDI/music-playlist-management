package com.musicplaylist.presentation.controller;

import com.musicplaylist.application.service.PlayerService;
import com.musicplaylist.domain.model.PlaybackState;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/play/{playlistId}")
    public PlaybackState play(@PathVariable Long playlistId) {
        return playerService.play(playlistId);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/pause")
    public PlaybackState pause() {
        return playerService.pause();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/next")
    public PlaybackState next() {
        return playerService.next();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/previous")
    public PlaybackState previous() {
        return playerService.previous();
    }

    @GetMapping("/state")
    public PlaybackState state() {
        return playerService.state();
    }
}
