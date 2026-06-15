package com.musicplaylist.application.service;

import com.musicplaylist.domain.model.Song;
import com.musicplaylist.domain.strategy.GenreBalancedShuffleStrategy;
import com.musicplaylist.domain.strategy.SmartShuffleStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShuffleStrategyTest {
    @Test
    void smartShuffleKeepsSameSize() {
        SmartShuffleStrategy strategy = new SmartShuffleStrategy();
        List<Song> songs = List.of(
                new Song(1L, "A", "Artist 1", "Album", "Rock", 100, "url1"),
                new Song(2L, "B", "Artist 1", "Album", "Rock", 100, "url2"),
                new Song(3L, "C", "Artist 2", "Album", "Pop", 100, "url3")
        );

        List<Song> result = strategy.shuffle(songs);

        assertEquals(3, result.size());
        assertTrue(result.containsAll(songs));
    }

    @Test
    void genreBalancedShuffleKeepsSameSize() {
        GenreBalancedShuffleStrategy strategy = new GenreBalancedShuffleStrategy();
        List<Song> songs = List.of(
                new Song(1L, "A", "Artist 1", "Album", "Rock", 100, "url1"),
                new Song(2L, "B", "Artist 2", "Album", "Pop", 100, "url2"),
                new Song(3L, "C", "Artist 3", "Album", "Rock", 100, "url3")
        );

        List<Song> result = strategy.shuffle(songs);

        assertEquals(3, result.size());
        assertTrue(result.containsAll(songs));
    }
}
