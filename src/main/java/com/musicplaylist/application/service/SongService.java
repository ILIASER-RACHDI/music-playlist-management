package com.musicplaylist.application.service;

import com.musicplaylist.application.dto.SongRequest;
import com.musicplaylist.application.dto.SongResponse;
import com.musicplaylist.infrastructure.persistence.entity.AlbumEntity;
import com.musicplaylist.infrastructure.persistence.entity.SongEntity;
import com.musicplaylist.infrastructure.persistence.repository.AlbumRepository;
import com.musicplaylist.infrastructure.persistence.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final MapperService mapper;

    public SongService(SongRepository songRepository, AlbumRepository albumRepository, MapperService mapper) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.mapper = mapper;
    }

    @Transactional
    public SongResponse create(Long artistId, Long albumId, SongRequest request) {
        AlbumEntity album = albumRepository.findById(albumId).orElseThrow(() -> new IllegalArgumentException("Album not found"));
        if (!album.getArtist().getId().equals(artistId)) {
            throw new IllegalArgumentException("Album does not belong to artist");
        }
        SongEntity song = new SongEntity();
        song.setTitle(request.title());
        song.setGenre(request.genre());
        song.setDuration(request.duration());
        song.setAudioUrl(request.audioUrl());
        song.setAlbum(album);
        song.setArtist(album.getArtist());
        return mapper.toSongResponse(songRepository.save(song));
    }

    @Transactional(readOnly = true)
    public List<SongResponse> findByAlbum(Long albumId) {
        return songRepository.findByAlbumId(albumId).stream().map(mapper::toSongResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<SongResponse> findAll() {
        return songRepository.findAll().stream().map(mapper::toSongResponse).toList();
    }
}
