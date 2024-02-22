package com.example.capstone.expense.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
