package com.musicplaylist.infrastructure.persistence.repository;

import com.musicplaylist.infrastructure.persistence.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
    List<PlaylistEntity> findByOwnerId(Long ownerId);
}
