package com.musicplaylist.presentation.controller;

import com.musicplaylist.application.dto.AlbumRequest;
import com.musicplaylist.application.dto.AlbumResponse;
import com.musicplaylist.application.dto.ArtistRequest;
import com.musicplaylist.application.dto.ArtistResponse;
import com.musicplaylist.application.dto.SongRequest;
import com.musicplaylist.application.dto.SongResponse;
import com.musicplaylist.application.service.AlbumService;
import com.musicplaylist.application.service.ArtistService;
import com.musicplaylist.application.service.SongService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final SongService songService;

    public ArtistController(ArtistService artistService, AlbumService albumService, SongService songService) {
        this.artistService = artistService;
        this.albumService = albumService;
        this.songService = songService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','ARTIST')")
    @PostMapping
    public ArtistResponse createArtist(@Valid @RequestBody ArtistRequest request) {
        return artistService.create(request);
    }

    @GetMapping
    public List<ArtistResponse> findAll() {
        return artistService.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','ARTIST')")
    @PostMapping("/{artistId}/albums")
    public AlbumResponse createAlbum(@PathVariable Long artistId, @Valid @RequestBody AlbumRequest request) {
        return albumService.create(artistId, request);
    }

    @GetMapping("/{artistId}/albums")
    public List<AlbumResponse> findAlbums(@PathVariable Long artistId) {
        return albumService.findByArtist(artistId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','ARTIST')")
    @PostMapping("/{artistId}/albums/{albumId}/songs")
    public SongResponse createSong(@PathVariable Long artistId, @PathVariable Long albumId, @Valid @RequestBody SongRequest request) {
        return songService.create(artistId, albumId, request);
    }
}
