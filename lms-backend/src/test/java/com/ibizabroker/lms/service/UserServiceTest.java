package com.ibizabroker.lms.service;

import com.ibizabroker.lms.dao.RoleRepository;
import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.entity.Role;
import com.ibizabroker.lms.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private Role adminRole;
    private Users adminUser;

    @BeforeEach
    void setUp() {
        // Mock Admin Role
        adminRole = new Role();
        adminRole.setRoleName("Admin");

        // Mock Admin User
        adminUser = new Users();
        adminUser.setUsername("admin");
        adminUser.setName("Administrator");
        adminUser.setPassword("hashedPassword");
        adminUser.setRole(Set.of(adminRole));
    }

    @Test
    void testCreateAdminUserIfNotExists_AdminDoesNotExist() {
        // Arrange: No admin user exists
        when(usersRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(roleRepository.findByRoleName("Admin")).thenReturn(Optional.of(adminRole));
        when(passwordEncoder.encode("admin123")).thenReturn("hashedPassword");
        when(usersRepository.save(any(Users.class))).thenReturn(adminUser);

        // Act
        userService.createAdminUserIfNotExists();

        // Assert: Admin user should be created
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testCreateAdminUserIfNotExists_AdminAlreadyExists() {
        // Arrange: Admin user already exists
        when(usersRepository.findByUsername("admin")).thenReturn(Optional.of(adminUser));

        // Act
        userService.createAdminUserIfNotExists();

        // Assert: Admin user should NOT be created again
        verify(usersRepository, never()).save(any(Users.class));
    }

    @Test
    void testCreateAdminUserIfNotExists_AdminRoleDoesNotExist() {
        // Arrange: No admin user, and admin role also doesn't exist
        when(usersRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(roleRepository.findByRoleName("Admin")).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenReturn(adminRole);
        when(passwordEncoder.encode("admin123")).thenReturn("hashedPassword");
        when(usersRepository.save(any(Users.class))).thenReturn(adminUser);

        // Act
        userService.createAdminUserIfNotExists();

        // Assert: Admin role and user should be created
        verify(roleRepository, times(1)).save(any(Role.class));
        verify(usersRepository, times(1)).save(any(Users.class));
    }
}
