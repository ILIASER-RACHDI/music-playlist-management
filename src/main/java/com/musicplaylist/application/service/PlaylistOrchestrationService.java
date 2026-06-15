package com.musicplaylist.application.service;

import com.musicplaylist.application.dto.PlaylistRequest;
import com.musicplaylist.application.dto.PlaylistResponse;
import com.musicplaylist.application.dto.SmartCreateRequest;
import com.musicplaylist.application.dto.SongResponse;
import com.musicplaylist.domain.model.PlaybackState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PlaylistOrchestrationService {
    private final PlaylistService playlistService;
    private final RecommendationService recommendationService;
    private final PlayerService playerService;

    public PlaylistOrchestrationService(PlaylistService playlistService, RecommendationService recommendationService, PlayerService playerService) {
        this.playlistService = playlistService;
        this.recommendationService = recommendationService;
        this.playerService = playerService;
    }

    @Transactional
    public Map<String, Object> smartCreate(SmartCreateRequest request) {
        PlaylistResponse playlist = playlistService.create(new PlaylistRequest(request.name(), request.description(), request.ownerId()));
        if (request.initialSongIds() != null) {
            request.initialSongIds().forEach(songId -> playlistService.addSong(playlist.id(), songId));
        }
        List<SongResponse> recommendations = recommendationService.recommend(playlist.id());
        PlaybackState playbackState = playerService.play(playlist.id());
        return Map.of("playlist", playlist, "recommendations", recommendations, "player", playbackState);
    }
}
