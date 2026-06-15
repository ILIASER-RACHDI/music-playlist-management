package com.musicplaylist.application.service;

import com.musicplaylist.application.dto.SongResponse;
import com.musicplaylist.infrastructure.persistence.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private final PlaylistService playlistService;
    private final SongRepository songRepository;
    private final MapperService mapper;

    public RecommendationService(PlaylistService playlistService, SongRepository songRepository, MapperService mapper) {
        this.playlistService = playlistService;
        this.songRepository = songRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<SongResponse> recommend(Long playlistId) {
        var playlistSongs = playlistService.getSongs(playlistId);
        Set<String> genres = playlistSongs.stream().map(song -> song.genre()).collect(Collectors.toSet());
        Set<Long> excludedIds = playlistSongs.stream().map(song -> song.id()).collect(Collectors.toSet());

        if (genres.isEmpty()) {
            return songRepository.findAll().stream().limit(10).map(mapper::toSongResponse).toList();
        }
        if (excludedIds.isEmpty()) {
            return songRepository.findTop10ByGenreIn(genres).stream().map(mapper::toSongResponse).toList();
        }
        return songRepository.findTop10ByGenreInAndIdNotIn(genres, excludedIds).stream()
                .map(mapper::toSongResponse)
                .toList();
    }
}
