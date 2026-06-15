package com.musicplaylist.infrastructure.persistence.repository;

import com.musicplaylist.infrastructure.persistence.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SongRepository extends JpaRepository<SongEntity, Long> {
    List<SongEntity> findByAlbumId(Long albumId);
    List<SongEntity> findTop10ByGenreInAndIdNotIn(Collection<String> genres, Collection<Long> excludedIds);
    List<SongEntity> findTop10ByGenreIn(Collection<String> genres);
}
