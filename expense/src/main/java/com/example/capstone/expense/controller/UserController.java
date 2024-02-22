package com.example.capstone.expense.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    // Retrieve all users
    @GetMapping("/users") 
    Collection<User> getAllUsers() {
        return userRepository.findAll();
    }
}
