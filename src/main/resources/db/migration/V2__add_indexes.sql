CREATE INDEX idx_albums_artist_id ON albums(artist_id);
CREATE INDEX idx_songs_album_id ON songs(album_id);
CREATE INDEX idx_songs_artist_id ON songs(artist_id);
CREATE INDEX idx_songs_genre ON songs(genre);
CREATE INDEX idx_playlists_owner_id ON playlists(owner_id);
CREATE INDEX idx_playlist_songs_position ON playlist_songs(playlist_id, position);
