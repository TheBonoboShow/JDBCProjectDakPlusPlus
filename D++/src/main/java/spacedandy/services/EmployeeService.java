package spacedandy.services;

import spacedandy.data.EmployeeDAO;
import spacedandy.model.EmployeeInfo;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public List<EmployeeInfo> getAllEmployees() throws SQLException {
        return employeeDAO.getAllEmployees();
    }

    public Optional<EmployeeInfo> getEmployeeById(int id) throws SQLException {
        return employeeDAO.getEmployeeById(id);
    }

    public void deleteEmployee(int id) throws SQLException {
        employeeDAO.deleteById(id);
    }

    public void addEmployee(EmployeeInfo newEmployee) throws SQLException {
        employeeDAO.addEmployee(newEmployee);
    }

    public void updateEmployee(int id, EmployeeInfo newEmployee) throws SQLException {
        employeeDAO.updateEmployee(id, newEmployee);
    }

    public List<EmployeeInfo> lookForName(String extract) throws SQLException {
        return employeeDAO.lookForName(extract);
    }

    public List<EmployeeInfo> upcomingBirthdays() throws SQLException {
        return employeeDAO.upcomingBirthdays();
    }

    public List<EmployeeInfo> todayBirthday() throws SQLException {
        return employeeDAO.todayBirthday();
    }
}
