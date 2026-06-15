package com.musicplaylist.presentation.controller;

import com.musicplaylist.application.dto.SongResponse;
import com.musicplaylist.application.service.SongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<SongResponse> findAll() {
        return songService.findAll();
    }
}
