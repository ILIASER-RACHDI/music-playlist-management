package com.musicplaylist.security;

import com.musicplaylist.TestFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {
    private final JwtService jwtService = new JwtService("0123456789012345678901234567890123456789012345678901234567890123", 60);

    @Test
    void generateToken() {
        var token = jwtService.generateToken(TestFixtures.getMockedUser());
        assertThat(token).isNotBlank();
    }

    @Test
    void extractUsername() {
        var token = jwtService.generateToken(TestFixtures.getMockedUser());
        assertThat(jwtService.extractUsername(token)).isEqualTo("user@test.com");
    }

    @Test
    void isValid() {
        var token = jwtService.generateToken(TestFixtures.getMockedUser());
        assertThat(jwtService.isValid(token, "user@test.com")).isTrue();
        assertThat(jwtService.isValid(token, "other@test.com")).isFalse();
    }
}
