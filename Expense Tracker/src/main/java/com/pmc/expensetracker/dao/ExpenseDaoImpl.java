package com.pmc.expensetracker.dao;

import com.pmc.expensetracker.model.Expense;
import com.pmc.expensetracker.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpenseDaoImpl implements ExpenseDao {

    @Override
    public Expense create(Expense expense) throws Exception {
        String sql = "insert into expenses (title, amount, category, expense_date) values (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, expense.getTitle());
            ps.setDouble(2, expense.getAmount());
            ps.setString(3, expense.getCategory());
            ps.setDate(4, Date.valueOf(expense.getExpenseDate()));
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) expense.setId(keys.getInt(1));
            }
        }
        return expense;
    }

    @Override
    public Optional<Expense> findById(int id) throws Exception {
        String sql = "select * from expenses where id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Expense> findAll() throws Exception {
        List<Expense> list = new ArrayList<>();
        String sql = "select * from expenses order by expense_date desc";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public boolean update(Expense expense) throws Exception {
        String sql = "update expenses set title=?, amount=?, category=?, expense_date=? where id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, expense.getTitle());
            ps.setDouble(2, expense.getAmount());
            ps.setString(3, expense.getCategory());
            ps.setDate(4, Date.valueOf(expense.getExpenseDate()));
            ps.setInt(5, expense.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "delete from expenses where id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Expense> findByCategory(String category) throws Exception {
        List<Expense> list = new ArrayList<>();
        String sql = "select * from expenses where category = ? order by expense_date desc";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    @Override
    public List<Expense> findByDateRange(LocalDate from, LocalDate to) throws Exception {
        List<Expense> list = new ArrayList<>();
        String sql = "select * from expenses where expense_date between ? and ? order by expense_date desc";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    @Override
    public double monthlyTotal(int year, int month) throws Exception {
        String sql = "select sum(amount) as total from expenses where year(expense_date)= ? and month(expense_date)= ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, year);
            ps.setInt(2, month);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("total");
            }
        }
        return 0;
    }

    private Expense mapRow(ResultSet rs) throws SQLException {
        return new Expense(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getDouble("amount"),
                rs.getString("category"),
                rs.getDate("expense_date").toLocalDate()
        );
    }
}
