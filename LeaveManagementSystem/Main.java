import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LeaveManager manager = new LeaveManager();

        while (true) {
            System.out.println("\n===== Leave Management System =====");
            System.out.println("1. Employee");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Choose role: ");
            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter Employee ID: ");
                int empId = sc.nextInt();
                System.out.print("Enter Employee Name: ");
                String name = sc.next();

                Employee emp = new Employee(empId, name);

                while (true) {
                    System.out.println("\n===== Employee Menu =====");
                    System.out.println("1. Apply for Leave");
                    System.out.println("2. View Leave History");
                    System.out.println("3. Back");
                    System.out.print("Choose: ");
                    int c = sc.nextInt();
                    if (c == 1) {
                        try {
                            System.out.print("Enter start date (yyyy-MM-dd): ");
                            LocalDate start = LocalDate.parse(sc.next());
                            System.out.print("Enter end date (yyyy-MM-dd): ");
                            LocalDate end = LocalDate.parse(sc.next());
                            System.out.print("Enter leave type: ");
                            String type = sc.next();
                            sc.nextLine();
                            System.out.print("Enter reason: ");
                            String reason = sc.nextLine();
                            manager.applyLeave(emp.getId(), start, end, type, reason);
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else if (c == 2) {
                        manager.viewHistory(emp.getId());
                    } else break;
                }

            } else if (choice == 2) {
                System.out.print("Enter Admin Name: ");
                String adminName = sc.next();
                Admin admin = new Admin(adminName);

                while (true) {
                    System.out.println("\n===== Admin Menu =====");
                    System.out.println("1. View All Leaves");
                    System.out.println("2. Approve/Reject Leave");
                    System.out.println("3. Back");
                    System.out.print("Choose: ");
                    int c = sc.nextInt();
                    if (c == 1) {
                        manager.viewAll();
                    } else if (c == 2) {
                        try {
                            System.out.print("Enter Leave ID: ");
                            int lid = sc.nextInt();
                            System.out.print("Approve (A) or Reject (R): ");
                            String action = sc.next();
                            if (action.equalsIgnoreCase("A"))
                                manager.updateLeaveStatus(lid, LeaveStatus.APPROVED);
                            else if (action.equalsIgnoreCase("R"))
                                manager.updateLeaveStatus(lid, LeaveStatus.REJECTED);
                            else
                                System.out.println("Invalid input!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else break;
                }

            } else {
                manager.saveToFile();
                System.out.println("Exiting... Data saved!");
                break;
            }
        }
        sc.close();
    }
}
