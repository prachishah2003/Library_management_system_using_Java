package com.ibizabroker.lms.controller;

import com.ibizabroker.lms.dao.BorrowRepository;
import com.ibizabroker.lms.dao.RequestedBookRepository;
import com.ibizabroker.lms.dao.RoleRepository;
import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.entity.Role;
import com.ibizabroker.lms.entity.Users;
import com.ibizabroker.lms.exceptions.NotFoundException;
import com.ibizabroker.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private RequestedBookRepository requestedBookRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserDetails(@PathVariable int userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }
    @PostMapping("/users")
    @PreAuthorize("hasRole('Admin')")
    public Users addUserByAdmin(@RequestBody Users user) {
        // Fetch or create the "User" role
        Role userRole = roleRepository.findByRoleName("User")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName("User");
                    return roleRepository.save(newRole);
                });

        // Create and save the new user
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword())); // Encode the password
        newUser.setRole(Collections.singleton(userRole));
        newUser.setAddress(user.getAddress());
        newUser.setAccountBalance(500.0);
        usersRepository.save(newUser);
        return newUser;
    }

    @PutMapping("/{userId}/add-balance")
    public ResponseEntity<Users> addBalance(@PathVariable int userId, @RequestParam double amount) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountBalance(user.getAccountBalance() + amount);
        usersRepository.save(user);

        return ResponseEntity.ok(user);
    }


    @GetMapping("/users")
    @PreAuthorize("hasRole('Admin')")
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id "+ id +" does not exist."));
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/users/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users userDetails) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id "+ id +" does not exist."));

        user.setName(userDetails.getName());
        user.setRole(userDetails.getRole());
        user.setUsername(userDetails.getUsername());
        user.setAddress(userDetails.getAddress());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        Users updatedUser = usersRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(@RequestParam Integer userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Prevent deletion if the user has borrowed books
        if (borrowRepository.existsByUserId(userId)) {
            return ResponseEntity.badRequest().body("User cannot be deleted as they have borrowed books.");
        }

        // Delete requested books by this user
        requestedBookRepository.deleteByRequestedById(userId);

        // Delete the user
        usersRepository.delete(user);

        return ResponseEntity.ok("User deleted successfully.");
    }

}
