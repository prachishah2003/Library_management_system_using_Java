package com.ibizabroker.lms.service;

import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.dao.RoleRepository;
import com.ibizabroker.lms.entity.RegisterRequest;
import com.ibizabroker.lms.entity.JwtResponse;
import com.ibizabroker.lms.entity.Role;
import com.ibizabroker.lms.entity.Users;
import com.ibizabroker.lms.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public JwtResponse registerUser(RegisterRequest request) {
        // Check if the user already exists
        Optional<Users> existingUser = usersRepository.findByUsername(request.getUsername());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User already exists!");
        }

        // Ensure the "ROLE_USER" role exists
        Role userRole = roleRepository.findByRoleName("User")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName("User");
                    return roleRepository.save(newRole);
                });

        // Create and save the new user
        Users newUser = new Users();
        newUser.setUsername(request.getUsername());
        newUser.setName(request.getName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword())); // Encode the password
        newUser.setRole(Collections.singleton(userRole));
        newUser.setAccountBalance(500.0);
        newUser.setAddress(request.getAddress());
        usersRepository.save(newUser);

        // Generate JWT Token without re-authenticating
        UserDetails userDetails = new User(
                newUser.getUsername(), newUser.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority( "ROLE_" +userRole.getRoleName()))
        );

        String token = jwtUtil.generateToken(userDetails);

        return new JwtResponse(newUser, token);
    }
}
