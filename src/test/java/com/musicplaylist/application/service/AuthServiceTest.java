package com.musicplaylist.application.service;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.application.dto.AuthRequest;
import com.musicplaylist.application.dto.RegisterRequest;
import com.musicplaylist.domain.model.Role;
import com.musicplaylist.infrastructure.persistence.repository.UserRepository;
import com.musicplaylist.security.JwtService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtService jwtService;
    @Mock AuthenticationManager authenticationManager;
    @InjectMocks AuthService authService;

    @Test
    void register() {
        var request = new RegisterRequest("user@test.com", "password", Role.ROLE_USER);
        when(userRepository.existsByEmail("user@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtService.generateToken(any())).thenReturn("token");
        var response = authService.register(request);
        assertThat(response.token()).isEqualTo("token");
        verify(userRepository).save(any());
    }

    @Test
    void login() {
        var request = new AuthRequest("user@test.com", "password");
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(TestFixtures.getMockedUser()));
        when(jwtService.generateToken(any())).thenReturn("token");
        var response = authService.login(request);
        assertThat(response.token()).isEqualTo("token");
        verify(authenticationManager).authenticate(any());
    }

    @Nested
    class RegisterAlreadyUsed {
        @Test
        void register() {
            var request = new RegisterRequest("user@test.com", "password", Role.ROLE_USER);
            when(userRepository.existsByEmail("user@test.com")).thenReturn(true);
            assertThatThrownBy(() -> authService.register(request)).isInstanceOf(IllegalArgumentException.class);
            verify(userRepository, never()).save(any());
        }
    }
}
