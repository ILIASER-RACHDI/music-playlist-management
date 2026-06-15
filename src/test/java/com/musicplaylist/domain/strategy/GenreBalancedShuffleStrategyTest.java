package com.musicplaylist.domain.strategy;

import com.musicplaylist.domain.model.ShuffleType;
import com.musicplaylist.domain.model.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GenreBalancedShuffleStrategyTest {
    private final GenreBalancedShuffleStrategy genreBalancedShuffleStrategy = new GenreBalancedShuffleStrategy();

    @Test
    void type() {
        assertThat(genreBalancedShuffleStrategy.type()).isEqualTo(ShuffleType.GENRE_BALANCED);
    }

    @Test
    void shuffle() {
        var songs = List.of(
                new Song(1L, "A", "Artist A", "Album", "POP", 100, "url-a"),
                new Song(2L, "B", "Artist B", "Album", "ROCK", 100, "url-b"),
                new Song(3L, "C", "Artist C", "Album", "POP", 100, "url-c")
        );
        var result = genreBalancedShuffleStrategy.shuffle(songs);
        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyInAnyOrderElementsOf(songs);
        assertThat(result.get(0).genre()).isEqualTo("POP");
        assertThat(result.get(1).genre()).isEqualTo("ROCK");
    }
}
