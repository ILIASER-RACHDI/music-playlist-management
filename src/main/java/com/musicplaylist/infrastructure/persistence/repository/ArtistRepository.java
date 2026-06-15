package com.musicplaylist.infrastructure.persistence.repository;

import com.musicplaylist.infrastructure.persistence.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
    Optional<ArtistEntity> findByUserId(Long userId);
}
