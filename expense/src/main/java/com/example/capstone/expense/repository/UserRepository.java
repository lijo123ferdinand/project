package com.example.capstone.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.capstone.expense.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
		
	User findByUsername(String username);		
}