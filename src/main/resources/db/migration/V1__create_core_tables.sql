CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE artists (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bio TEXT,
    user_id BIGINT UNIQUE,
    CONSTRAINT fk_artists_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE albums (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_date DATE,
    artist_id BIGINT NOT NULL,
    CONSTRAINT fk_albums_artist FOREIGN KEY (artist_id) REFERENCES artists(id)
);

CREATE TABLE songs (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    duration INTEGER NOT NULL,
    audio_url VARCHAR(1000),
    album_id BIGINT NOT NULL,
    artist_id BIGINT NOT NULL,
    CONSTRAINT fk_songs_album FOREIGN KEY (album_id) REFERENCES albums(id),
    CONSTRAINT fk_songs_artist FOREIGN KEY (artist_id) REFERENCES artists(id)
);

CREATE TABLE playlists (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    owner_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_playlists_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE playlist_songs (
    playlist_id BIGINT NOT NULL,
    song_id BIGINT NOT NULL,
    position INTEGER NOT NULL,
    added_at TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (playlist_id, song_id),
    CONSTRAINT fk_playlist_songs_playlist FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE CASCADE,
    CONSTRAINT fk_playlist_songs_song FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE
);
