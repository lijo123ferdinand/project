package com.example.capstone.expense.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.capstone.expense.model.User;
import com.example.capstone.expense.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class AuthController {
    
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Admin login endpoint
    @PostMapping("/admin/login")
        public ResponseEntity<String> adminLogin(@RequestBody User adminLogin) {
        // Check if the credentials match the admin email and password
        if ("admin@email.com".equals(adminLogin.getEmail()) && "admin".equals(adminLogin.getPassword())) {
            return ResponseEntity.ok("Admin login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid admin credentials");
        }
    }


    // Retrieve all users
    @GetMapping("/admin/users") 
    Collection<User> getAllUsers() {
        return userRepository.findAll();
    }
}
