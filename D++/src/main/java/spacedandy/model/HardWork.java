package spacedandy.model;

public class HardWork {
    private String firstName;
    private String lastName;
    private double hoursWorked;

    @Override
    public String toString() {
        return  "{firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hoursWorkedTotal=" + hoursWorked + "}";
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

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
