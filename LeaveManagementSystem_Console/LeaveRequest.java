public class LeaveRequest {
    private static int counter = 1;
    private int id;
    private Employee employee;
    private String startDate;
    private String endDate;
    private String type;
    private String reason;
    private String status;

    public LeaveRequest(Employee employee, String startDate, String endDate, String type, String reason) {
        this.id = counter++;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.reason = reason;
        this.status = "Pending";
    }

    public int getId() { 
        return id; 
    }
    public Employee getEmployee() { 
        return employee; 
    }
    public String getStartDate() { 
        return startDate; 
    }
    public String getEndDate() { 
        return endDate; 
    }
    public String getType() { 
        return type; 
    }
    public String getReason() { 
        return reason; 
    }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + "," + employee.getName() + "," + startDate + "," + endDate + "," + type + "," + reason + "," + status;
    }

    public static LeaveRequest fromCSV(String csv) {
        String[] parts = csv.split(",");
        Employee emp = new Employee(parts[1]);
        LeaveRequest lr = new LeaveRequest(emp, parts[2], parts[3], parts[4], parts[5]);
        lr.id = Integer.parseInt(parts[0]);
        lr.status = parts[6];
        return lr;
    }
}
