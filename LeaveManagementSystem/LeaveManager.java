import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class LeaveManager {
    private List<LeaveRequest> leaveRequests;
    private int nextLeaveId = 1;
    private static final String FILE_NAME = "leaves.csv";

    public LeaveManager() {
        leaveRequests = new ArrayList<>();
        loadFromFile();
    }

    public void applyLeave(int employeeId, LocalDate start, LocalDate end, String type, String reason) throws Exception {
        if (end.isBefore(start)) {
            throw new Exception("End date cannot be before start date!");
        }
        for (LeaveRequest req : leaveRequests) {
            if (req.getEmployeeId() == employeeId && req.getStatus() != LeaveStatus.REJECTED) {
                if (!(end.isBefore(req.getStartDate()) || start.isAfter(req.getEndDate()))) {
                    throw new Exception("Overlapping leave exists!");
                }
            }
        }
        LeaveRequest newLeave = new LeaveRequest(nextLeaveId++, employeeId, start, end, type, reason);
        leaveRequests.add(newLeave);
        System.out.println("Leave applied successfully: " + newLeave);
    }

    public void updateLeaveStatus(int leaveId, LeaveStatus status) throws Exception {
        for (LeaveRequest req : leaveRequests) {
            if (req.getLeaveId() == leaveId) {
                req.setStatus(status);
                System.out.println("Leave updated: " + req);
                return;
            }
        }
        throw new Exception("Leave ID not found!");
    }

    public void viewHistory(int employeeId) {
        for (LeaveRequest req : leaveRequests) {
            if (req.getEmployeeId() == employeeId) {
                System.out.println(req);
            }
        }
    }

    public void viewAll() {
        for (LeaveRequest req : leaveRequests) {
            System.out.println(req);
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                LeaveRequest req = LeaveRequest.fromCSV(line);
                leaveRequests.add(req);
                nextLeaveId = Math.max(nextLeaveId, req.getLeaveId() + 1);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (LeaveRequest req : leaveRequests) {
                bw.write(req.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
