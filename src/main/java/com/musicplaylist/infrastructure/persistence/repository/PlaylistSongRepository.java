package com.musicplaylist.infrastructure.persistence.repository;

import com.musicplaylist.infrastructure.persistence.entity.PlaylistSongEntity;
import com.musicplaylist.infrastructure.persistence.entity.PlaylistSongId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSongEntity, PlaylistSongId> {
    List<PlaylistSongEntity> findByPlaylistIdOrderByPositionAsc(Long playlistId);
    void deleteByPlaylistIdAndSongId(Long playlistId, Long songId);
    int countByPlaylistId(Long playlistId);
}
