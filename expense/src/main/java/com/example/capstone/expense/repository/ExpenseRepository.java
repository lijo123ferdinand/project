package com.example.capstone.expense.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.capstone.expense.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByCategory(String category);
		
}