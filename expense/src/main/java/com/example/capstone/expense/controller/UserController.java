package com.example.capstone.expense.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.capstone.expense.model.User;
import com.example.capstone.expense.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
    
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // // Retrieve all users
    // @GetMapping("/users") 
    // Collection<User> getAllUsers() {
    //     return userRepository.findAll();
    // }

    // Retrieve users by email
    // @GetMapping("/usersByEmail")
    // Collection<User> getUsersByEmail(@RequestParam String email) {
    //     return userRepository.findByEmail(email);
    // }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User newUser) {
        // Check if user with the same email already exists
        User existingUser = userRepository.findByEmail(newUser.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email already exists");
        }

        // Save the new user
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }   

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        // Find the user by email
        User existingUser = userRepository.findByEmail(loginUser.getEmail());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // Verify the password
        if (!existingUser.getPassword().equals(loginUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // Authentication successful
        return ResponseEntity.ok("Login successful");
    }

}