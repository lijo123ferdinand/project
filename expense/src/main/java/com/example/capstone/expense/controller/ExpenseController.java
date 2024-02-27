package com.example.capstone.expense.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
// import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.capstone.expense.model.Expense;
import com.example.capstone.expense.model.User;
import com.example.capstone.expense.repository.ExpenseRepository;
import com.example.capstone.expense.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class ExpenseController {
    
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public ExpenseController(UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    // Retrieve expenses by user email
    @GetMapping("/expensesByEmail")
    Collection<Expense> getExpensesByEmail(@RequestParam String email) {
        return expenseRepository.findByUserEmail(email);
    }

    @GetMapping("/user/expensesByDate")
    public Collection<Expense> getExpensesByEmailAndDate(
        @RequestParam String email,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expense_date) {

        // Find the user by email
        User user = userRepository.findByEmail(email);

        // Check if user exists
        if (user == null) {
            return Collections.emptyList(); // Return empty list if user not found
        }

        // Query expenses based on user and date
        return expenseRepository.findByUserAndExpenseDate(user, expense_date);
    }

    

}
