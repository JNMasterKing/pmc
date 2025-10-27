package com.pmc.expensetracker;

import com.pmc.expensetracker.dao.ExpenseDaoImpl;
import com.pmc.expensetracker.model.Expense;
import com.pmc.expensetracker.service.ExpenseService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        ExpenseService service = new ExpenseService(new ExpenseDaoImpl());
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Expense Tracker ===");
            System.out.println("1. Add Expense");
            System.out.println("2. Update Expense");
            System.out.println("3. Delete Expense");
            System.out.println("4. View All Expenses");
            System.out.println("5. Filter by Category");
            System.out.println("6. Filter by Date Range");
            System.out.println("7. Monthly Total");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            String ch = sc.nextLine();

            try {
                switch (ch) {
                    case "1" -> addExpense(sc, service);
                    case "2" -> updateExpense(sc, service);
                    case "3" -> deleteExpense(sc, service);
                    case "4" -> listAll(service);
                    case "5" -> filterByCategory(sc, service);
                    case "6" -> filterByDate(sc, service);
                    case "7" -> monthlyTotal(sc, service);
                    case "0" -> {
                        System.out.println("Goodbye!");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addExpense(Scanner sc, ExpenseService service) throws Exception {
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Amount: ");
        double amount = Double.parseDouble(sc.nextLine());
        System.out.print("Category: ");
        String category = sc.nextLine();
        System.out.print("Date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(sc.nextLine(), DATE_TIME_FORMATTER);
        Expense saved = service.addExpense(new Expense(title, amount, category, date));
        System.out.println("Added: " + saved);
    }

    private static void updateExpense(Scanner sc, ExpenseService service) throws Exception {
        System.out.print("Expense ID to update: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New Title: ");
        String title = sc.nextLine();
        System.out.print("New Amount: ");
        double amount = Double.parseDouble(sc.nextLine());
        System.out.print("New Category: ");
        String category = sc.nextLine();
        System.out.print("New Date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(sc.nextLine(), DATE_TIME_FORMATTER);
        boolean ok = service.updateExpense(new Expense(id, title, amount, category, date));
        System.out.println(ok ? "Updated successfully." : "Update failed.");
    }

    private static void deleteExpense(Scanner sc, ExpenseService service) throws Exception {
        System.out.print("Expense ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean ok = service.deleteExpense(id);
        System.out.println(ok ? "Deleted successfully." : "No record found.");
    }

    private static void listAll(ExpenseService service) throws Exception {
        List<Expense> list = service.listAll();
        list.forEach(System.out::println);
    }

    private static void filterByCategory(Scanner sc, ExpenseService service) throws Exception {
        System.out.print("Category: ");
        String category = sc.nextLine();
        List<Expense> list = service.filterByCategory(category);
        list.forEach(System.out::println);
    }

    private static void filterByDate(Scanner sc, ExpenseService service) throws Exception {
        System.out.print("From (yyyy-MM-dd): ");
        LocalDate from = LocalDate.parse(sc.nextLine(), DATE_TIME_FORMATTER);
        System.out.print("To (yyyy-MM-dd): ");
        LocalDate to = LocalDate.parse(sc.nextLine(), DATE_TIME_FORMATTER);
        List<Expense> list = service.filterByDateRange(from, to);
        list.forEach(System.out::println);
    }

    private static void monthlyTotal(Scanner sc, ExpenseService service) throws Exception {
        System.out.print("Year (YYYY): ");
        int year = Integer.parseInt(sc.nextLine());
        System.out.print("Month (1-12): ");
        int month = Integer.parseInt(sc.nextLine());
        double total = service.monthlyTotal(year, month);
        System.out.printf("Total for %d-%02d = %.2f%n", year, month, total);
    }
}
