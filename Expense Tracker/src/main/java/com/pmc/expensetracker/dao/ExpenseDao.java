package com.pmc.expensetracker.dao;

import com.pmc.expensetracker.model.Expense;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseDao {
    Expense create(Expense expense) throws Exception;

    Optional<Expense> findById(int id) throws Exception;

    List<Expense> findAll() throws Exception;

    boolean update(Expense expense) throws Exception;

    boolean delete(int id) throws Exception;

    List<Expense> findByCategory(String category) throws Exception;

    List<Expense> findByDateRange(LocalDate from, LocalDate to) throws Exception;

    double monthlyTotal(int year, int month) throws Exception;
}
