package com.example.capstone.expense.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.capstone.expense.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
		
	User findByUsername(String username);

    User findByEmail(String email);		
}