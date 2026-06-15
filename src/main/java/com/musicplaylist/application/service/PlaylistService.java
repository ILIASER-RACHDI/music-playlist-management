package com.musicplaylist.application.service;

import com.musicplaylist.application.dto.PlaylistRequest;
import com.musicplaylist.application.dto.PlaylistResponse;
import com.musicplaylist.application.dto.ReorderRequest;
import com.musicplaylist.domain.model.Song;
import com.musicplaylist.infrastructure.persistence.entity.PlaylistEntity;
import com.musicplaylist.infrastructure.persistence.entity.PlaylistSongEntity;
import com.musicplaylist.infrastructure.persistence.entity.PlaylistSongId;
import com.musicplaylist.infrastructure.persistence.entity.SongEntity;
import com.musicplaylist.infrastructure.persistence.entity.UserEntity;
import com.musicplaylist.infrastructure.persistence.repository.PlaylistRepository;
import com.musicplaylist.infrastructure.persistence.repository.PlaylistSongRepository;
import com.musicplaylist.infrastructure.persistence.repository.SongRepository;
import com.musicplaylist.infrastructure.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final MapperService mapper;

    public PlaylistService(PlaylistRepository playlistRepository, PlaylistSongRepository playlistSongRepository, SongRepository songRepository, UserRepository userRepository, MapperService mapper) {
        this.playlistRepository = playlistRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    public PlaylistResponse create(PlaylistRequest request) {
        UserEntity owner = userRepository.findById(request.ownerId()).orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setName(request.name());
        playlist.setDescription(request.description());
        playlist.setOwner(owner);
        return mapper.toPlaylistResponse(playlistRepository.save(playlist));
    }

    @Transactional(readOnly = true)
    public PlaylistResponse findById(Long id) {
        return mapper.toPlaylistResponse(getPlaylist(id));
    }

    @Transactional(readOnly = true)
    public List<PlaylistResponse> findByOwner(Long ownerId) {
        return playlistRepository.findByOwnerId(ownerId).stream().map(mapper::toPlaylistResponse).toList();
    }

    @Transactional
    public PlaylistResponse update(Long id, PlaylistRequest request) {
        PlaylistEntity playlist = getPlaylist(id);
        playlist.setName(request.name());
        playlist.setDescription(request.description());
        return mapper.toPlaylistResponse(playlistRepository.save(playlist));
    }

    @Transactional
    public void delete(Long id) {
        playlistRepository.deleteById(id);
    }

    @Transactional
    public void addSong(Long playlistId, Long songId) {
        PlaylistEntity playlist = getPlaylist(playlistId);
        SongEntity song = songRepository.findById(songId).orElseThrow(() -> new IllegalArgumentException("Song not found"));
        PlaylistSongId id = new PlaylistSongId(playlistId, songId);
        if (playlistSongRepository.existsById(id)) {
            return;
        }
        PlaylistSongEntity playlistSong = new PlaylistSongEntity();
        playlistSong.setId(id);
        playlistSong.setPlaylist(playlist);
        playlistSong.setSong(song);
        playlistSong.setPosition(playlistSongRepository.countByPlaylistId(playlistId));
        playlistSongRepository.save(playlistSong);
    }

    @Transactional
    public void removeSong(Long playlistId, Long songId) {
        playlistSongRepository.deleteByPlaylistIdAndSongId(playlistId, songId);
        normalizePositions(playlistId);
    }

    @Transactional
    public void reorder(Long playlistId, ReorderRequest request) {
        List<PlaylistSongEntity> current = playlistSongRepository.findByPlaylistIdOrderByPositionAsc(playlistId);
        for (int i = 0; i < request.songIds().size(); i++) {
            Long songId = request.songIds().get(i);
            int finalI = i;
            current.stream()
                    .filter(item -> item.getSong().getId().equals(songId))
                    .findFirst()
                    .ifPresent(item -> item.setPosition(finalI));
        }
        playlistSongRepository.saveAll(current);
    }

    @Transactional(readOnly = true)
    public List<Song> getSongs(Long playlistId) {
        return playlistSongRepository.findByPlaylistIdOrderByPositionAsc(playlistId).stream()
                .map(PlaylistSongEntity::getSong)
                .map(mapper::toDomainSong)
                .toList();
    }

    @Transactional
    public void replaceOrder(Long playlistId, List<Song> songs) {
        List<PlaylistSongEntity> current = playlistSongRepository.findByPlaylistIdOrderByPositionAsc(playlistId);
        for (int i = 0; i < songs.size(); i++) {
            Long songId = songs.get(i).id();
            final int position = i;
            current.stream()
                    .filter(item -> item.getSong().getId().equals(songId))
                    .findFirst()
                    .ifPresent(item -> item.setPosition(position));
        }
        playlistSongRepository.saveAll(current);
    }

    PlaylistEntity getPlaylist(Long id) {
        return playlistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Playlist not found"));
    }

    private void normalizePositions(Long playlistId) {
        List<PlaylistSongEntity> songs = playlistSongRepository.findByPlaylistIdOrderByPositionAsc(playlistId);
        for (int i = 0; i < songs.size(); i++) {
            songs.get(i).setPosition(i);
        }
        playlistSongRepository.saveAll(songs);
    }
}
