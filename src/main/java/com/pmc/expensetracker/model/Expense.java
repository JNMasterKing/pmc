package com.pmc.expensetracker.model;

import java.time.LocalDate;

public class Expense {
    private Integer id;
    private String title;
    private double amount;
    private String category;
    private LocalDate expenseDate;

    public Expense() {}

    public Expense(Integer id, String title, double amount, String category, LocalDate expenseDate) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.expenseDate = expenseDate;
    }

    public Expense(String title, double amount, String category, LocalDate expenseDate) {
        this(null, title, amount, category, expenseDate);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }


    @Override
    public String toString() {
        return String.format("Expense{  id=%d, title='%s', amount=%.2f, category='%s', date=%s}",
                id, title, amount, category, expenseDate);
    }
}
