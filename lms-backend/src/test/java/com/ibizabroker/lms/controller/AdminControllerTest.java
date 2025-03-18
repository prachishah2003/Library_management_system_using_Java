package com.ibizabroker.lms.controller;

import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.entity.Users;
import com.ibizabroker.lms.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminController adminController;

    private Users mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new Users();
        mockUser.setUserId(1);
        mockUser.setUsername("adminUser");
        mockUser.setName("Admin");
        mockUser.setPassword("plaintext");
    }

    @Test
    void testAddUserByAdmin() {
        // Arrange: Encrypt password before saving
        when(passwordEncoder.encode("plaintext")).thenReturn("encryptedPassword");
        when(usersRepository.save(any(Users.class))).thenReturn(mockUser);

        // Act
        Users savedUser = adminController.addUserByAdmin(mockUser);

        // Assert
        assertNotNull(savedUser);
        assertEquals("adminUser", savedUser.getUsername());
        verify(passwordEncoder, times(1)).encode("plaintext");
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testGetAllUsers() {
        // Arrange: Mock user list
        List<Users> userList = Arrays.asList(mockUser, new Users());
        when(usersRepository.findAll()).thenReturn(userList);

        // Act
        List<Users> result = adminController.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        verify(usersRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success() {
        // Arrange: User exists
        when(usersRepository.findById(1)).thenReturn(Optional.of(mockUser));

        // Act
        ResponseEntity<Users> response = adminController.getUserById(1);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody());
        verify(usersRepository, times(1)).findById(1);
    }

    @Test
    void testGetUserById_NotFound() {
        // Arrange: User does not exist
        when(usersRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotFoundException.class, () -> adminController.getUserById(1));
        assertEquals("User with id 1 does not exist.", exception.getMessage());
        verify(usersRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange: User exists
        Users updatedDetails = new Users();
        updatedDetails.setName("Updated Name");
        updatedDetails.setUsername("updatedUsername");

        when(usersRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(usersRepository.save(any(Users.class))).thenReturn(mockUser);

        // Act
        ResponseEntity<Users> response = adminController.updateUser(1, updatedDetails);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Name", mockUser.getName());
        assertEquals("updatedUsername", mockUser.getUsername());

        verify(usersRepository, times(1)).findById(1);
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        // Arrange: User does not exist
        Users updatedDetails = new Users();
        when(usersRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotFoundException.class, () -> adminController.updateUser(1, updatedDetails));
        assertEquals("User with id 1 does not exist.", exception.getMessage());
        verify(usersRepository, times(1)).findById(1);
        verify(usersRepository, never()).save(any());
    }
}

