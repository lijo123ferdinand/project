package com.example.capstone.expense.controller;

import java.math.BigDecimal;
// import java.sql.Date;
import java.util.Collection;
import java.util.Date;

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

    //  TRY RESOLVING THE ERROR IN THIS POSTMAPPING METHOD.
    
    // @PostMapping("/expenses/{user_id}")
    // public ResponseEntity<String> addExpense(@PathVariable("user_id") Long userId, @RequestBody Expense newExpense) {

    //     // Set the current date for the new expense
    //     newExpense.setExpenseDate(new Date());

    //      // Retrieve the user associated with the provided user_id
    //      User user = userRepository.findById(userId).orElse(null);

    //     if (user == null) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    //     }

    //     // Subtract the new expense amount from the user's balance
    //     BigDecimal newBalance = user.getBalance().subtract(newExpense.getAmount());
    //     if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
    //     }

    //     // Update the user's balance in the database
    //     user.setBalance(newBalance);
    //     userRepository.save(user);

    //     // Save the new expense
    //     expenseRepository.save(newExpense);

    //     return ResponseEntity.status(HttpStatus.CREATED).body("Expense added successfully");
    // }
    // @RequestMapping(value = "/expenses", method = RequestMethod.POST)
    // public ResponseEntity<String> addExpense(User user, Expense newExpense) {
    
    //     // Set the current date for the new expense
    //     newExpense.setExpenseDate(new Date());
    //     System.out.println(user.getId());
    //     // Retrieve the user associated with the provided user_id
    //     User existingUser = userRepository.findById(user.getId()).orElse(null);
    
    //     if (existingUser == null) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not User found");
    //     }
    
    //     // Subtract the new expense amount from the user's balance
    //     BigDecimal newBalance = existingUser.getBalance().subtract(newExpense.getAmount());
    //     if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
    //     }
    
    //     // Update the user's balance in the database
    //     existingUser.setBalance(newBalance);
    //     userRepository.save(existingUser);
    
    //     // Save the new expense
    //     newExpense.setUser(existingUser); // Associate the expense with the existing user
    //     expenseRepository.save(newExpense);
    
    //     return ResponseEntity.status(HttpStatus.CREATED).body("Expense added successfully");
    // }
    

}
