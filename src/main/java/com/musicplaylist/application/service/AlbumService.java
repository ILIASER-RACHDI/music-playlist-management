package com.musicplaylist.application.service;

import com.musicplaylist.application.dto.AlbumRequest;
import com.musicplaylist.application.dto.AlbumResponse;
import com.musicplaylist.infrastructure.persistence.entity.AlbumEntity;
import com.musicplaylist.infrastructure.persistence.entity.ArtistEntity;
import com.musicplaylist.infrastructure.persistence.repository.AlbumRepository;
import com.musicplaylist.infrastructure.persistence.repository.ArtistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final MapperService mapper;

    public AlbumService(AlbumRepository albumRepository, ArtistRepository artistRepository, MapperService mapper) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.mapper = mapper;
    }

    @Transactional
    public AlbumResponse create(Long artistId, AlbumRequest request) {
        ArtistEntity artist = artistRepository.findById(artistId).orElseThrow(() -> new IllegalArgumentException("Artist not found"));
        AlbumEntity album = new AlbumEntity();
        album.setTitle(request.title());
        album.setReleaseDate(request.releaseDate());
        album.setArtist(artist);
        return mapper.toAlbumResponse(albumRepository.save(album));
    }

    @Transactional(readOnly = true)
    public List<AlbumResponse> findByArtist(Long artistId) {
        return albumRepository.findByArtistId(artistId).stream().map(mapper::toAlbumResponse).toList();
    }
}
