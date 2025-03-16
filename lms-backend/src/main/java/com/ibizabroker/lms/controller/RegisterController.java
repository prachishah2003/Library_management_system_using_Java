package com.ibizabroker.lms.controller;

import com.ibizabroker.lms.entity.RegisterRequest;
import com.ibizabroker.lms.entity.JwtResponse;
import com.ibizabroker.lms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class RegisterController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            JwtResponse jwtResponse = authService.registerUser(request);
            return ResponseEntity.ok(jwtResponse);  // Send token + user details
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Return error if user exists
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while registering."); // Generic error
        }
    }
}


