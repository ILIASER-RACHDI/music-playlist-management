package com.musicplaylist.application.service;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.domain.export.M3UExportStrategy;
import com.musicplaylist.domain.model.ExportFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExportServiceTest {
    @Mock PlaylistService playlistService;

    @Test
    void export() {
        var exportService = new ExportService(playlistService, List.of(new M3UExportStrategy()));
        when(playlistService.findById(1L)).thenReturn(TestFixtures.getMockedPlaylistResponse());
        when(playlistService.getSongs(1L)).thenReturn(TestFixtures.getMockedDomainSongs());
        var result = exportService.export(1L, ExportFormat.M3U);
        assertThat(result).contains("#EXTM3U");
        assertThat(result).contains("One More Time");
    }

    @org.junit.jupiter.api.Nested
    class ExportUnsupported {
        @Test
        void export() {
            var exportService = new ExportService(playlistService, List.of());
            assertThatThrownBy(() -> exportService.export(1L, ExportFormat.M3U)).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
