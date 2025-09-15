import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LeaveRequest {
    private int leaveId;
    private int employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String reason;
    private LeaveStatus status;

    public LeaveRequest(int leaveId, int employeeId, LocalDate startDate, LocalDate endDate, String type, String reason) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.reason = reason;
        this.status = LeaveStatus.PENDING;
    }

    public int getLeaveId() { return leaveId; }
    public int getEmployeeId() { return employeeId; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getType() { return type; }
    public String getReason() { return reason; }
    public LeaveStatus getStatus() { return status; }
    public void setStatus(LeaveStatus status) { this.status = status; }

    public String toCSV() {
        return leaveId + "," + employeeId + "," + startDate + "," + endDate + "," + type + "," + reason + "," + status;
    }

    public static LeaveRequest fromCSV(String line) {
        String[] parts = line.split(",");
        int leaveId = Integer.parseInt(parts[0]);
        int employeeId = Integer.parseInt(parts[1]);
        LocalDate start = LocalDate.parse(parts[2]);
        LocalDate end = LocalDate.parse(parts[3]);
        String type = parts[4];
        String reason = parts[5];
        LeaveStatus status = LeaveStatus.valueOf(parts[6]);

        LeaveRequest req = new LeaveRequest(leaveId, employeeId, start, end, type, reason);
        req.setStatus(status);
        return req;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Leave ID: " + leaveId +
                ", Employee ID: " + employeeId +
                ", " + startDate.format(fmt) + " to " + endDate.format(fmt) +
                ", Type: " + type +
                ", Reason: " + reason +
                ", Status: " + status;
    }
}
