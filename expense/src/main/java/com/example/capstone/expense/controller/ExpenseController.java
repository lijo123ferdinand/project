package com.example.capstone.expense.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import java.util.Date;
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

import com.example.capstone.expense.dto.ExpenseRequest;
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

    // @GetMapping("/user/expensesByDate")
    // public Collection<Expense> getExpensesByEmailAndDate(
    //     @RequestParam String email,
    //     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expense_date) {

    //     // Find the user by email
    //     User user = userRepository.findByEmail(email);

    //     // Check if user exists
    //     if (user == null) {
    //         System.out.println("User Not Found!");
    //         return Collections.emptyList(); // Return empty list if user not found
    //     }

    //     // Query expenses based on user and date
    //     return expenseRepository.findByUserAndExpenseDate(user, expense_date);
    // }
    @GetMapping("/user/expensesByDate")
    public Collection<Expense> getExpensesByEmailAndDate(
            @RequestParam String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime expense_date) {

        // Find the user by email
        User user = userRepository.findByEmail(email);

        // Check if user exists
        if (user == null) {
            System.out.println("User Not Found!");
            return Collections.emptyList(); // Return empty list if user not found
        }

        // Query expenses based on user and date
        return expenseRepository.findByUserAndExpenseDate(user, expense_date);
    }

    // Add expense to a user  
    @PostMapping("/user/expenses")
    public ResponseEntity<String> addExpense(@RequestBody ExpenseRequest expenseRequest) {
        // Retrieve the user by email
        User user = userRepository.findByEmail(expenseRequest.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

         // Check if the user has sufficient balance
        BigDecimal expenseAmount = expenseRequest.getAmount();
        BigDecimal currentBalance = user.getBalance();
        if (currentBalance.compareTo(expenseAmount) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
        }

        // Update the user's balance
        BigDecimal newBalance = currentBalance.subtract(expenseAmount);
        user.setBalance(newBalance);

        // Create a new Expense object
        Expense newExpense = new Expense();
        newExpense.setUser(user);
        newExpense.setCategory(expenseRequest.getCategory());
        newExpense.setAmount(expenseRequest.getAmount());

        // Convert LocalDate to Date
        Date expenseDate = Date.valueOf(LocalDate.now());
        newExpense.setExpenseDate(expenseDate);

        // Save the new expense
        expenseRepository.save(newExpense);

         // Save the updated user balance
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Expense added successfully");
    }

    //Retrieve expenses of a user by DateRange
    // @GetMapping("/user/expensesByDateRange")
    // Collection<Expense> getExpensesByDateRange(
    //         @RequestParam String email,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
    //     return expenseRepository.findByUserEmailAndExpenseDateBetween(email, startDate, endDate);
    // }
    @GetMapping("/user/expensesByDateRange")
    Collection<Expense> getExpensesByDateRange(
            @RequestParam String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return expenseRepository.findByUserEmailAndExpenseDateBetween(email, startDate, endDate);
    }
    @GetMapping("/user/expensesByEachCategory")
    public Collection<Expense> getExpensesByCategory(
            @RequestParam String email,
            @RequestParam String category) {
        // Retrieve expenses for the given user and category
        return expenseRepository.findByUserEmailAndCategory(email, category);
    }
    @GetMapping("/user/totalExpenseByCategoryAndDateRange")
    public Map<String, BigDecimal> getTotalExpenseByCategoryAndDateRange(
            @RequestParam String email,
            @RequestParam String category,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        // Retrieve expenses for the given user, category, and date range
        Collection<Expense> expenses = expenseRepository.findByUserEmailAndCategoryAndExpenseDateBetween(email, category, startDate, endDate);
 
        // Calculate total expense for the category
        BigDecimal totalExpense = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
 
        // Create a map to hold the result
        Map<String, BigDecimal> result = new HashMap<>();
        result.put(category, totalExpense);
 
        return result;
    }
 
    
    @GetMapping("/user/expensesByCategory")
        public Map<String, BigDecimal> getExpensesByCategory(@RequestParam String email) {
            // Find the user by email
            User user = userRepository.findByEmail(email);
            // Check if user exists
            if (user == null) {
                System.out.println("User Not Found!");
                return Collections.emptyMap(); // Return empty map if user not found
            }
            // Fetch all expenses for the user
            List<Expense> userExpenses = expenseRepository.findByUser(user);
            // Map to store category-wise expenses
            Map<String, BigDecimal> expensesByCategory = new HashMap<>();
            // Calculate total expenses for each category
            for (Expense expense : userExpenses) {
                String category = expense.getCategory();
                BigDecimal amount = expense.getAmount();
                // Get the current total for the category or zero if not present
                BigDecimal currentTotal = expensesByCategory.getOrDefault(category, BigDecimal.ZERO);
                // Add the current expense amount to the total
                expensesByCategory.put(category, currentTotal.add(amount));
            }
            return expensesByCategory;
        }
            @GetMapping("/user/totalExpenses")
        public ResponseEntity<BigDecimal> getTotalExpenses(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        Collection<Expense> expenses = expenseRepository.findByUser(user);
        BigDecimal totalExpenses = expenses.stream()
                                        .map(Expense::getAmount)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return ResponseEntity.status(HttpStatus.OK).body(totalExpenses);
    }
    



}
