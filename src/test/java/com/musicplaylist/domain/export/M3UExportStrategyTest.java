package com.musicplaylist.domain.export;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.domain.model.ExportFormat;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class M3UExportStrategyTest {
    private final M3UExportStrategy m3UExportStrategy = new M3UExportStrategy();

    @Test
    void format() {
        assertThat(m3UExportStrategy.format()).isEqualTo(ExportFormat.M3U);
    }

    @Test
    void export() {
        var result = m3UExportStrategy.export("Playlist", TestFixtures.getMockedDomainSongs());
        assertThat(result).startsWith("#EXTM3U");
        assertThat(result).contains("One More Time");
    }
}
