package com.musicplaylist.domain.strategy;

import com.musicplaylist.domain.model.ShuffleType;
import com.musicplaylist.domain.model.Song;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RandomShuffleStrategy implements ShuffleStrategy {
    @Override
    public ShuffleType type() {
        return ShuffleType.RANDOM;
    }

    @Override
    public List<Song> shuffle(List<Song> songs) {
        List<Song> result = new ArrayList<>(songs);
        Collections.shuffle(result);
        return result;
    }
}
