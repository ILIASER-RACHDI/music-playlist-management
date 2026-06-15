package com.musicplaylist.domain.strategy;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.domain.model.ShuffleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RandomShuffleStrategyTest {
    private final RandomShuffleStrategy randomShuffleStrategy = new RandomShuffleStrategy();

    @Test
    void type() {
        assertThat(randomShuffleStrategy.type()).isEqualTo(ShuffleType.RANDOM);
    }

    @Test
    void shuffle() {
        var songs = TestFixtures.getMockedDomainSongs();
        var result = randomShuffleStrategy.shuffle(songs);
        assertThat(result).hasSameSizeAs(songs);
        assertThat(result).containsExactlyInAnyOrderElementsOf(songs);
    }
}
