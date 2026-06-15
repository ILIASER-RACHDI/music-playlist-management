package com.musicplaylist.application.service;

import com.musicplaylist.domain.export.ExportStrategy;
import com.musicplaylist.domain.model.ExportFormat;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class ExportService {
    private final PlaylistService playlistService;
    private final Map<ExportFormat, ExportStrategy> strategies = new EnumMap<>(ExportFormat.class);

    public ExportService(PlaylistService playlistService, List<ExportStrategy> strategyList) {
        this.playlistService = playlistService;
        strategyList.forEach(strategy -> strategies.put(strategy.format(), strategy));
    }

    public String export(Long playlistId, ExportFormat format) {
        ExportStrategy strategy = strategies.get(format);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported export format");
        }
        String playlistName = playlistService.findById(playlistId).name();
        return strategy.export(playlistName, playlistService.getSongs(playlistId));
    }
}
