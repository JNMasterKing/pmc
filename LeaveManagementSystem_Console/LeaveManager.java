import java.io.*;
import java.util.*;

public class LeaveManager {
    private List<LeaveRequest> leaves;
    private final String FILE_NAME = "leaves.csv";

    public LeaveManager() {
        leaves = new ArrayList<>();
        loadData();
    }

    public boolean applyLeave(LeaveRequest lr) {
        // check overlap
        for (LeaveRequest existing : leaves) {
            if (existing.getEmployee().getName().equals(lr.getEmployee().getName())
                    && existing.getStatus().equals("Approved")
                    && datesOverlap(existing.getStartDate(), existing.getEndDate(), lr.getStartDate(), lr.getEndDate())) {
                return false; // overlapping leave
            }
        }
        leaves.add(lr);
        return true;
    }

    public void viewAllLeaves() {
        if (leaves.isEmpty()) {
            System.out.println("No leave requests.");
            return;
        }
        for (LeaveRequest lr : leaves) {
            System.out.println(lr.getId() + " | " + lr.getEmployee().getName() + " | " +
                    lr.getStartDate() + " to " + lr.getEndDate() + " | " +
                    lr.getType() + " | " + lr.getReason() + " | " + lr.getStatus());
        }
    }

    public void updateLeaveStatus(int id, String status) {
        for (LeaveRequest lr : leaves) {
            if (lr.getId() == id) {
                lr.setStatus(status);
                System.out.println("Leave ID " + id + " updated to " + status);
                return;
            }
        }
        System.out.println("Leave ID not found.");
    }

    private boolean datesOverlap(String s1, String e1, String s2, String e2) {
        return !(e1.compareTo(s2) < 0 || e2.compareTo(s1) < 0);
    }

    public void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (LeaveRequest lr : leaves) {
                bw.write(lr.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadData() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                leaves.add(LeaveRequest.fromCSV(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
