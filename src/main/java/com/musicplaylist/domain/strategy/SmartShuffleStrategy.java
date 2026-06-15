package com.musicplaylist.domain.strategy;

import com.musicplaylist.domain.model.ShuffleType;
import com.musicplaylist.domain.model.Song;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class SmartShuffleStrategy implements ShuffleStrategy {
    @Override
    public ShuffleType type() {
        return ShuffleType.SMART;
    }

    @Override
    public List<Song> shuffle(List<Song> songs) {
        List<Song> remaining = new ArrayList<>(songs);
        remaining.sort(Comparator.comparing(Song::artistName));
        List<Song> result = new ArrayList<>();
        String lastArtist = null;

        while (!remaining.isEmpty()) {
            int selectedIndex = 0;
            for (int i = 0; i < remaining.size(); i++) {
                if (!remaining.get(i).artistName().equals(lastArtist)) {
                    selectedIndex = i;
                    break;
                }
            }
            Song selected = remaining.remove(selectedIndex);
            result.add(selected);
            lastArtist = selected.artistName();
        }
        return result;
    }
}
