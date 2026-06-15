package com.musicplaylist.domain.export;

import com.musicplaylist.domain.model.ExportFormat;
import com.musicplaylist.domain.model.Song;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class M3UExportStrategy implements ExportStrategy {
    @Override
    public ExportFormat format() {
        return ExportFormat.M3U;
    }

    @Override
    public String export(String playlistName, List<Song> songs) {
        StringBuilder builder = new StringBuilder("#EXTM3U\n");
        for (Song song : songs) {
            builder.append("#EXTINF:")
                    .append(song.duration())
                    .append(",")
                    .append(song.artistName())
                    .append(" - ")
                    .append(song.title())
                    .append("\n")
                    .append(song.audioUrl())
                    .append("\n");
        }
        return builder.toString();
    }
}
