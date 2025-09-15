import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LeaveManager manager = new LeaveManager();

        
        Admin admin = new Admin("admin", "password");

        System.out.println("===== Leave Management System =====");

        while (true) {
            System.out.println("\nChoose Role:");
            System.out.println("1. Admin");
            System.out.println("2. Employee");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String uname = sc.nextLine();
                    System.out.print("Enter password: ");
                    String pass = sc.nextLine();

                    if (uname.equals(admin.getUsername()) && pass.equals(admin.getPassword())) {
                        System.out.println("Welcome Admin!");
                        adminMenu(admin, manager, sc);
                    } else {
                        System.out.println("Invalid admin credentials!");
                    }
                    break;

                case 2:
                    System.out.print("Enter employee name: ");
                    String empName = sc.nextLine();
                    Employee emp = new Employee(empName);

                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    String start = sc.nextLine();
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    String end = sc.nextLine();
                    System.out.print("Enter leave type (Sick/Normal/Other): ");
                    String type = sc.nextLine();
                    System.out.print("Enter reason: ");
                    String reason = sc.nextLine();

                    LeaveRequest lr = new LeaveRequest(emp, start, end, type, reason);
                    if (manager.applyLeave(lr)) {
                        System.out.println("Leave applied successfully!");
                    } else {
                        System.out.println("Leave application failed (invalid/overlapping).");
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    manager.saveData();
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void adminMenu(Admin admin, LeaveManager manager, Scanner sc) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View leave requests");
            System.out.println("2. Approve leave");
            System.out.println("3. Reject leave");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    manager.viewAllLeaves();
                    break;
                case 2:
                    System.out.print("Enter leave ID to approve: ");
                    int idApprove = sc.nextInt();
                    sc.nextLine();
                    manager.updateLeaveStatus(idApprove, "Approved");
                    break;
                case 3:
                    System.out.print("Enter leave ID to reject: ");
                    int idReject = sc.nextInt();
                    sc.nextLine();
                    manager.updateLeaveStatus(idReject, "Rejected");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
