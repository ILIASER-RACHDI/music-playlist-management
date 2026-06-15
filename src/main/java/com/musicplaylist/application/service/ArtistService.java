package com.musicplaylist.application.service;

import com.musicplaylist.application.dto.ArtistRequest;
import com.musicplaylist.application.dto.ArtistResponse;
import com.musicplaylist.infrastructure.persistence.entity.ArtistEntity;
import com.musicplaylist.infrastructure.persistence.entity.UserEntity;
import com.musicplaylist.infrastructure.persistence.repository.ArtistRepository;
import com.musicplaylist.infrastructure.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final MapperService mapper;

    public ArtistService(ArtistRepository artistRepository, UserRepository userRepository, MapperService mapper) {
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ArtistResponse create(ArtistRequest request) {
        ArtistEntity artist = new ArtistEntity();
        artist.setName(request.name());
        artist.setBio(request.bio());
        if (request.userId() != null) {
            UserEntity user = userRepository.findById(request.userId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            artist.setUser(user);
        }
        return mapper.toArtistResponse(artistRepository.save(artist));
    }

    @Transactional(readOnly = true)
    public List<ArtistResponse> findAll() {
        return artistRepository.findAll().stream().map(mapper::toArtistResponse).toList();
    }
}
