package com.musicplaylist.domain.strategy;

import com.musicplaylist.domain.model.ShuffleType;
import com.musicplaylist.domain.model.Song;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SmartShuffleStrategyTest {
    private final SmartShuffleStrategy smartShuffleStrategy = new SmartShuffleStrategy();

    @Test
    void type() {
        assertThat(smartShuffleStrategy.type()).isEqualTo(ShuffleType.SMART);
    }

    @Test
    void shuffle() {
        var songs = List.of(
                new Song(1L, "A", "Artist A", "Album", "POP", 100, "url-a"),
                new Song(2L, "B", "Artist A", "Album", "POP", 100, "url-b"),
                new Song(3L, "C", "Artist B", "Album", "POP", 100, "url-c")
        );
        var result = smartShuffleStrategy.shuffle(songs);
        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyInAnyOrderElementsOf(songs);
    }
}
