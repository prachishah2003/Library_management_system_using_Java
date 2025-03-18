package com.ibizabroker.lms.service;

import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.entity.JwtRequest;
import com.ibizabroker.lms.entity.JwtResponse;
import com.ibizabroker.lms.entity.Users;
import com.ibizabroker.lms.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private JwtService jwtService;

    private Users mockUser;

    @BeforeEach
    void setUp() {
        // Create a mock user
        mockUser = new Users();
        mockUser.setUsername("testUser");
        mockUser.setPassword("password123");
        mockUser.setRole(Set.of());  // Assuming roles are managed as a Set

        // Mock repository behavior
        when(usersRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
    }

    @Test
    void testCreateJwtToken_Success() throws Exception {
        // Arrange
        JwtRequest request = new JwtRequest("testUser", "password123");
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("mockedToken");
        doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act
        JwtResponse response = jwtService.createJwtToken(request);

        // Assert
        assertNotNull(response);
        assertEquals("mockedToken", response.getJwtToken());
        assertEquals("testUser", response.getUser().getUsername());
    }

    @Test
    void testCreateJwtToken_InvalidCredentials() {
        // Arrange
        JwtRequest request = new JwtRequest("testUser", "wrongPassword");
        doThrow(new BadCredentialsException("INVALID_CREDENTIALS"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> jwtService.createJwtToken(request));
        assertEquals("INVALID_CREDENTIALS", exception.getMessage());
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Act
        UserDetails userDetails = jwtService.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(usersRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> jwtService.loadUserByUsername("unknownUser"));
    }
}

