package com.musicplaylist.application.service;

import com.musicplaylist.application.dto.SongResponse;
import com.musicplaylist.domain.model.ShuffleType;
import com.musicplaylist.domain.model.Song;
import com.musicplaylist.domain.strategy.ShuffleStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class ShuffleService {
    private final PlaylistService playlistService;
    private final MapperService mapper;
    private final Map<ShuffleType, ShuffleStrategy> strategies = new EnumMap<>(ShuffleType.class);

    public ShuffleService(PlaylistService playlistService, MapperService mapper, List<ShuffleStrategy> strategyList) {
        this.playlistService = playlistService;
        this.mapper = mapper;
        strategyList.forEach(strategy -> strategies.put(strategy.type(), strategy));
    }

    @Transactional
    public List<SongResponse> shuffle(Long playlistId, ShuffleType type) {
        ShuffleStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported shuffle strategy");
        }
        List<Song> shuffled = strategy.shuffle(playlistService.getSongs(playlistId));
        playlistService.replaceOrder(playlistId, shuffled);
        return shuffled.stream().map(mapper::toSongResponse).toList();
    }
}
