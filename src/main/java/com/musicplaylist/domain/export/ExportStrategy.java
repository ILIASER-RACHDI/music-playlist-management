package com.musicplaylist.domain.export;

import com.musicplaylist.domain.model.ExportFormat;
import com.musicplaylist.domain.model.Song;

import java.util.List;

public interface ExportStrategy {
    ExportFormat format();
    String export(String playlistName, List<Song> songs);
}
