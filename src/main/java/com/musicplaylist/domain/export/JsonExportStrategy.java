package com.musicplaylist.domain.export;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicplaylist.domain.model.ExportFormat;
import com.musicplaylist.domain.model.Song;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class JsonExportStrategy implements ExportStrategy {
    private final ObjectMapper objectMapper;

    public JsonExportStrategy(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ExportFormat format() {
        return ExportFormat.JSON;
    }

    @Override
    public String export(String playlistName, List<Song> songs) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(Map.of("playlist", playlistName, "songs", songs));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to export playlist as JSON", e);
        }
    }
}
