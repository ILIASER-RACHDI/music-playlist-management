package com.musicplaylist.application.service;

import com.musicplaylist.application.dto.AlbumResponse;
import com.musicplaylist.application.dto.ArtistResponse;
import com.musicplaylist.application.dto.PlaylistResponse;
import com.musicplaylist.application.dto.SongResponse;
import com.musicplaylist.domain.model.Song;
import com.musicplaylist.infrastructure.persistence.entity.AlbumEntity;
import com.musicplaylist.infrastructure.persistence.entity.ArtistEntity;
import com.musicplaylist.infrastructure.persistence.entity.PlaylistEntity;
import com.musicplaylist.infrastructure.persistence.entity.SongEntity;
import org.springframework.stereotype.Service;

@Service
public class MapperService {
    public ArtistResponse toArtistResponse(ArtistEntity artist) {
        return new ArtistResponse(artist.getId(), artist.getName(), artist.getBio());
    }

    public AlbumResponse toAlbumResponse(AlbumEntity album) {
        return new AlbumResponse(album.getId(), album.getTitle(), album.getReleaseDate(), album.getArtist().getId());
    }

    public Song toDomainSong(SongEntity song) {
        return new Song(
                song.getId(),
                song.getTitle(),
                song.getArtist().getName(),
                song.getAlbum().getTitle(),
                song.getGenre(),
                song.getDuration(),
                song.getAudioUrl()
        );
    }

    public SongResponse toSongResponse(SongEntity song) {
        Song domainSong = toDomainSong(song);
        return new SongResponse(
                domainSong.id(),
                domainSong.title(),
                domainSong.artistName(),
                domainSong.albumTitle(),
                domainSong.genre(),
                domainSong.duration(),
                domainSong.audioUrl()
        );
    }

    public SongResponse toSongResponse(Song song) {
        return new SongResponse(song.id(), song.title(), song.artistName(), song.albumTitle(), song.genre(), song.duration(), song.audioUrl());
    }

    public PlaylistResponse toPlaylistResponse(PlaylistEntity playlist) {
        return new PlaylistResponse(playlist.getId(), playlist.getName(), playlist.getDescription(), playlist.getOwner().getId());
    }
}
