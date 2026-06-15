package com.musicplaylist.domain.strategy;

import com.musicplaylist.domain.model.ShuffleType;
import com.musicplaylist.domain.model.Song;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

@Component
public class GenreBalancedShuffleStrategy implements ShuffleStrategy {
    @Override
    public ShuffleType type() {
        return ShuffleType.GENRE_BALANCED;
    }

    @Override
    public List<Song> shuffle(List<Song> songs) {
        Map<String, Queue<Song>> byGenre = songs.stream()
                .collect(Collectors.groupingBy(Song::genre, LinkedHashMap::new, Collectors.toCollection(LinkedList::new)));

        List<Song> result = new ArrayList<>();
        boolean added;
        do {
            added = false;
            for (Queue<Song> queue : byGenre.values()) {
                Song song = queue.poll();
                if (song != null) {
                    result.add(song);
                    added = true;
                }
            }
        } while (added);
        return result;
    }
}
