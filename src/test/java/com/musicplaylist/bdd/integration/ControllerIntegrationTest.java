package com.musicplaylist.bdd.integration;

import org.springframework.boot.test.web.server.LocalServerPort;

public abstract class ControllerIntegrationTest {

    @LocalServerPort
    protected int port;

    protected String url(String path) {
        return "http://localhost:" + port + path;
    }
}