package com.musicplaylist.security;

import com.musicplaylist.TestFixtures;
import com.musicplaylist.infrastructure.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock UserRepository userRepository;
    @InjectMocks CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername() {
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(TestFixtures.getMockedUser()));
        var result = customUserDetailsService.loadUserByUsername("user@test.com");
        assertThat(result.getUsername()).isEqualTo("user@test.com");
        assertThat(result.getAuthorities()).extracting("authority").contains("ROLE_USER");
    }

    @org.junit.jupiter.api.Nested
    class LoadUserByUsernameNotFound {
        @Test
        void loadUserByUsername() {
            when(userRepository.findByEmail("missing@test.com")).thenReturn(Optional.empty());
            assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername("missing@test.com"))
                    .isInstanceOf(UsernameNotFoundException.class);
        }
    }
}
