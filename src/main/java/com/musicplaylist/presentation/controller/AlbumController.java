package com.musicplaylist.presentation.controller;

import com.musicplaylist.application.dto.SongResponse;
import com.musicplaylist.application.service.SongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    private final SongService songService;

    public AlbumController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/{albumId}/songs")
    public List<SongResponse> findSongs(@PathVariable Long albumId) {
        return songService.findByAlbum(albumId);
    }
}
