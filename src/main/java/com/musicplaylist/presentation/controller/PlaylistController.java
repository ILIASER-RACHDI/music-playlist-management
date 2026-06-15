package com.musicplaylist.presentation.controller;

import com.musicplaylist.application.dto.PlaylistRequest;
import com.musicplaylist.application.dto.PlaylistResponse;
import com.musicplaylist.application.dto.ReorderRequest;
import com.musicplaylist.application.dto.SmartCreateRequest;
import com.musicplaylist.application.dto.SongResponse;
import com.musicplaylist.application.service.ExportService;
import com.musicplaylist.application.service.MapperService;
import com.musicplaylist.application.service.PlaylistOrchestrationService;
import com.musicplaylist.application.service.PlaylistService;
import com.musicplaylist.application.service.RecommendationService;
import com.musicplaylist.application.service.ShuffleService;
import com.musicplaylist.domain.model.ExportFormat;
import com.musicplaylist.domain.model.ShuffleType;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;
    private final ShuffleService shuffleService;
    private final ExportService exportService;
    private final RecommendationService recommendationService;
    private final PlaylistOrchestrationService orchestrationService;
    private final MapperService mapper;

    public PlaylistController(PlaylistService playlistService, ShuffleService shuffleService, ExportService exportService, RecommendationService recommendationService, PlaylistOrchestrationService orchestrationService, MapperService mapper) {
        this.playlistService = playlistService;
        this.shuffleService = shuffleService;
        this.exportService = exportService;
        this.recommendationService = recommendationService;
        this.orchestrationService = orchestrationService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public PlaylistResponse create(@Valid @RequestBody PlaylistRequest request) {
        return playlistService.create(request);
    }

    @GetMapping("/{id}")
    public PlaylistResponse findById(@PathVariable Long id) {
        return playlistService.findById(id);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PlaylistResponse> findByOwner(@PathVariable Long ownerId) {
        return playlistService.findByOwner(ownerId);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public PlaylistResponse update(@PathVariable Long id, @Valid @RequestBody PlaylistRequest request) {
        return playlistService.update(id, request);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        playlistService.delete(id);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{playlistId}/songs/{songId}")
    public void addSong(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistService.addSong(playlistId, songId);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{playlistId}/songs/{songId}")
    public void removeSong(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistService.removeSong(playlistId, songId);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{playlistId}/songs/reorder")
    public void reorder(@PathVariable Long playlistId, @Valid @RequestBody ReorderRequest request) {
        playlistService.reorder(playlistId, request);
    }

    @GetMapping("/{playlistId}/songs")
    public List<SongResponse> getSongs(@PathVariable Long playlistId) {
        return playlistService.getSongs(playlistId).stream().map(mapper::toSongResponse).toList();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{playlistId}/shuffle")
    public List<SongResponse> shuffle(@PathVariable Long playlistId, @RequestParam ShuffleType strategy) {
        return shuffleService.shuffle(playlistId, strategy);
    }

    @GetMapping(value = "/{playlistId}/export", produces = MediaType.TEXT_PLAIN_VALUE)
    public String export(@PathVariable Long playlistId, @RequestParam ExportFormat format) {
        return exportService.export(playlistId, format);
    }

    @GetMapping("/{playlistId}/recommendations")
    public List<SongResponse> recommend(@PathVariable Long playlistId) {
        return recommendationService.recommend(playlistId);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/smart-create")
    public Map<String, Object> smartCreate(@Valid @RequestBody SmartCreateRequest request) {
        return orchestrationService.smartCreate(request);
    }
}
