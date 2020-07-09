package spacedandy.model;

public class EmployeeInfo {
    private int id;
    private String firstName;
    private String lastName;
    private String Tel;
    private String telEmergency;
    private String dateOfBirth;
    private double salary;

    @Override
    public String toString() {
        return "EmployeeInfo{" +
                "id=" + getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", Tel='" + Tel + '\'' +
                ", telEmergency='" + telEmergency + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", salary=" + salary +
                '}';
    }

    public String toStringNoId() {
        return "EmployeeInfo{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", Tel='" + Tel + '\'' +
                ", telEmergency='" + telEmergency + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", salary=" + salary +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getTelEmergency() {
        return telEmergency;
    }

    public void setTelEmergency(String telEmergency) {
        this.telEmergency = telEmergency;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
