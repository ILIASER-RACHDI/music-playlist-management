package com.musicplaylist.domain.strategy;

import com.musicplaylist.domain.model.ShuffleType;
import com.musicplaylist.domain.model.Song;

import java.util.List;

public interface ShuffleStrategy {
    ShuffleType type();
    List<Song> shuffle(List<Song> songs);
}
