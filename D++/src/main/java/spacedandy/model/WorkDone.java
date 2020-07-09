package spacedandy.model;

public class WorkDone {

    private int employeeId;
    private int projectId;
    private String date;
    private double hoursWorked;
    private String remarks;

    @Override
    public String toString() {
        return "WorkDone{" +
                "employeeId=" + employeeId +
                ", projectId=" + projectId +
                ", date='" + date + '\'' +
                ", hoursWorked=" + hoursWorked +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
