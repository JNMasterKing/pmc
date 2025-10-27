package com.pmc.expensetracker.service;

import com.pmc.expensetracker.dao.ExpenseDao;
import com.pmc.expensetracker.model.Expense;

import java.time.LocalDate;
import java.util.List;

public class ExpenseService {
    private final ExpenseDao dao;

    public ExpenseService(ExpenseDao dao) {
        this.dao = dao;
    }

    public Expense addExpense(Expense e) throws Exception {
        validate(e, false);
        return dao.create(e);
    }

    public boolean updateExpense(Expense e) throws Exception {
        validate(e, true);
        return dao.update(e);
    }

    public boolean deleteExpense(int id) throws Exception {
        return dao.delete(id);
    }

    public List<Expense> listAll() throws Exception {
        return dao.findAll();
    }

    public List<Expense> filterByCategory(String category) throws Exception {
        return dao.findByCategory(category);
    }

    public List<Expense> filterByDateRange(LocalDate from, LocalDate to) throws Exception {
        return dao.findByDateRange(from, to);
    }

    public double monthlyTotal(int year, int month) throws Exception {
        return dao.monthlyTotal(year, month);
    }

    private void validate(Expense e, boolean isUpdate) {
        if (isUpdate && (e.getId() == null || e.getId() <= 0)) {
            throw new IllegalArgumentException("Expense ID required for update.");
        }
        if (e.getTitle() == null || e.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (e.getAmount() < 0) {
            throw new IllegalArgumentException("Amount must not be negative.");
        }
        if (e.getCategory() == null || e.getCategory().isBlank()) {
            throw new IllegalArgumentException("Category cannot be empty.");
        }
        if (e.getExpenseDate() == null) {
            throw new IllegalArgumentException("Expense date is required.");
        }
    }
}
