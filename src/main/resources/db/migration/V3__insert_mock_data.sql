-- V3__insert_mock_data.sql

TRUNCATE TABLE playlist_songs RESTART IDENTITY CASCADE;
TRUNCATE TABLE playlists RESTART IDENTITY CASCADE;
TRUNCATE TABLE songs RESTART IDENTITY CASCADE;
TRUNCATE TABLE albums RESTART IDENTITY CASCADE;
TRUNCATE TABLE artists RESTART IDENTITY CASCADE;
TRUNCATE TABLE users RESTART IDENTITY CASCADE;

-- USERS
-- password for all users : password

INSERT INTO users (id, email, password, role)
VALUES
    (1, 'admin@music.com', '$2a$10$3L7g7gKEZVvGlSsu1ryoVuZMkhUO2cJjMgncqcY.DQnPjN.YZCJpO', 'ROLE_ADMIN'),
    (2, 'shakira.artist@music.com', '$2a$10$3L7g7gKEZVvGlSsu1ryoVuZMkhUO2cJjMgncqcY.DQnPjN.YZCJpO', 'ROLE_ARTIST'),
    (3, 'michael.artist@music.com', '$2a$10$3L7g7gKEZVvGlSsu1ryoVuZMkhUO2cJjMgncqcY.DQnPjN.YZCJpO', 'ROLE_ARTIST'),
    (4, 'dualipa.artist@music.com', '$2a$10$3L7g7gKEZVvGlSsu1ryoVuZMkhUO2cJjMgncqcY.DQnPjN.YZCJpO', 'ROLE_ARTIST'),
    (5, 'weeknd.artist@music.com', '$2a$10$3L7g7gKEZVvGlSsu1ryoVuZMkhUO2cJjMgncqcY.DQnPjN.YZCJpO', 'ROLE_ARTIST'),
    (6, 'user1@music.com', '$2a$10$3L7g7gKEZVvGlSsu1ryoVuZMkhUO2cJjMgncqcY.DQnPjN.YZCJpO', 'ROLE_USER'),
    (7, 'user2@music.com', '$2a$10$3L7g7gKEZVvGlSsu1ryoVuZMkhUO2cJjMgncqcY.DQnPjN.YZCJpO', 'ROLE_USER'),
    (8, 'admin@test.com', '$2a$10$3L7g7gKEZVvGlSsu1ryoVuZMkhUO2cJjMgncqcY.DQnPjN.YZCJpO', 'ROLE_ADMIN');

-- ARTISTS

INSERT INTO artists (id, name, bio, user_id)
VALUES
    (1, 'Shakira', 'Colombian singer and songwriter known for global Latin pop hits.', 2),
    (2, 'Michael Jackson', 'Legendary American singer known as the King of Pop.', 3),
    (3, 'Dua Lipa', 'British pop singer known for dance-pop and modern hits.', 4),
    (4, 'The Weeknd', 'Canadian singer blending RnB, synthwave and pop.', 5);

-- ALBUMS

INSERT INTO albums (id, title, artist_id, release_date)
VALUES
    (1, 'Laundry Service', 1, '2001-11-13'),
    (2, 'Oral Fixation Vol. 2', 1, '2005-11-28'),
    (3, 'Thriller', 2, '1982-11-30'),
    (4, 'Bad', 2, '1987-08-31'),
    (5, 'Future Nostalgia', 3, '2020-03-27'),
    (6, 'After Hours', 4, '2020-03-20');

-- SONGS

INSERT INTO songs (
    id,
    title,
    genre,
    duration,
    audio_url,
    album_id,
    artist_id
)
VALUES
    (1, 'Whenever Wherever', 'LATIN_POP', 196, 'https://cdn.music.com/shakira/whenever-wherever.mp3', 1, 1),
    (2, 'Underneath Your Clothes', 'POP', 224, 'https://cdn.music.com/shakira/underneath-your-clothes.mp3', 1, 1),
    (3, 'Hips Dont Lie', 'LATIN_POP', 218, 'https://cdn.music.com/shakira/hips-dont-lie.mp3', 2, 1),
    (4, 'Billie Jean', 'POP', 294, 'https://cdn.music.com/michael-jackson/billie-jean.mp3', 3, 2),
    (5, 'Beat It', 'ROCK_POP', 258, 'https://cdn.music.com/michael-jackson/beat-it.mp3', 3, 2),
    (6, 'Thriller', 'POP', 357, 'https://cdn.music.com/michael-jackson/thriller.mp3', 3, 2),
    (7, 'Smooth Criminal', 'POP', 257, 'https://cdn.music.com/michael-jackson/smooth-criminal.mp3', 4, 2),
    (8, 'Dont Start Now', 'DANCE_POP', 183, 'https://cdn.music.com/dua-lipa/dont-start-now.mp3', 5, 3),
    (9, 'Levitating', 'DANCE_POP', 203, 'https://cdn.music.com/dua-lipa/levitating.mp3', 5, 3),
    (10, 'Physical', 'DANCE_POP', 193, 'https://cdn.music.com/dua-lipa/physical.mp3', 5, 3),
    (11, 'Blinding Lights', 'SYNTH_POP', 200, 'https://cdn.music.com/the-weeknd/blinding-lights.mp3', 6, 4),
    (12, 'Save Your Tears', 'POP', 215, 'https://cdn.music.com/the-weeknd/save-your-tears.mp3', 6, 4);

-- PLAYLISTS

INSERT INTO playlists (
    id,
    name,
    description,
    owner_id,
    created_at,
    updated_at
)
VALUES
    (1, 'Pop Legends', 'Legendary international pop songs.', 6, NOW(), NOW()),
    (2, 'Workout Energy', 'Energetic songs for workouts and cardio.', 7, NOW(), NOW()),
    (3, 'Late Night Chill', 'Relaxing music for late night sessions.', 6, NOW(), NOW()),
    (4, 'Latin Vibes', 'Latin pop and dance music.', 7, NOW(), NOW());

-- PLAYLIST SONGS

INSERT INTO playlist_songs (
    playlist_id,
    song_id,
    position,
    added_at
)
VALUES
    (1, 4, 1, NOW()),
    (1, 6, 2, NOW()),
    (1, 11, 3, NOW()),
    (1, 12, 4, NOW()),
    (2, 8, 1, NOW()),
    (2, 9, 2, NOW()),
    (2, 10, 3, NOW()),
    (2, 5, 4, NOW()),
    (3, 2, 1, NOW()),
    (3, 12, 2, NOW()),
    (4, 1, 1, NOW()),
    (4, 3, 2, NOW());

-- RESET POSTGRES SEQUENCES

SELECT setval('users_id_seq', 8, true);
SELECT setval('artists_id_seq', 4, true);
SELECT setval('albums_id_seq', 6, true);
SELECT setval('songs_id_seq', 12, true);
SELECT setval('playlists_id_seq', 4, true);