package com.musicplaylist.domain.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicplaylist.TestFixtures;
import com.musicplaylist.domain.model.ExportFormat;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonExportStrategyTest {
    private final JsonExportStrategy jsonExportStrategy = new JsonExportStrategy(new ObjectMapper());

    @Test
    void format() {
        assertThat(jsonExportStrategy.format()).isEqualTo(ExportFormat.JSON);
    }

    @Test
    void export() {
        var result = jsonExportStrategy.export("Playlist", TestFixtures.getMockedDomainSongs());
        assertThat(result).contains("Playlist");
        assertThat(result).contains("One More Time");
    }
}
