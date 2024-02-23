package com.example.capstone.expense.controller;

// import java.sql.Date;
import java.util.Collection;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.capstone.expense.model.Expense;
import com.example.capstone.expense.repository.ExpenseRepository;

@RestController
@RequestMapping("/api")
public class ExpenseController {
    
    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    
    // Retrieve all expenses
    @GetMapping("/expenses") 
    Collection<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // Retrieve expenses by user email
    @GetMapping("/expensesByEmail")
    Collection<Expense> getExpensesByEmail(@RequestParam String email) {
        return expenseRepository.findByUserEmail(email);
    }

    // @PostMapping("/expenses")
    //     public ResponseEntity<String> addExpense(@RequestBody Expense newExpense) {
    //     // Set the current date for the new expense
    //     newExpense.setExpenseDate(new Date());

    //     // Save the new expense
    //     expenseRepository.save(newExpense);

    //     return ResponseEntity.status(HttpStatus.CREATED).body("Expense added successfully");
    // }

}
