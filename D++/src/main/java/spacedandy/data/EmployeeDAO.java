package spacedandy.data;

import spacedandy.model.EmployeeInfo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO {
    public List<EmployeeInfo> getAllEmployees() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from EmployeeInfo");
        return parseEmployees(rs);
    }

    public Optional<EmployeeInfo> getEmployeeById(int id) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from EmployeeInfo where Id=?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        List<EmployeeInfo> employee = parseEmployees(rs);
        if (employee.size() == 1) return Optional.of(employee.get(0));
        return Optional.empty();
    }

    private List<EmployeeInfo> parseEmployees(ResultSet rs) throws SQLException {
        List<EmployeeInfo> employees = new ArrayList<>();
        while (rs.next()) {
            EmployeeInfo employeeInfo = new EmployeeInfo();
            employeeInfo.setId(rs.getInt(1));
            employeeInfo.setFirstName(rs.getString(2));
            employeeInfo.setLastName(rs.getString(3));
            employeeInfo.setTel(rs.getString(4));
            employeeInfo.setTelEmergency(rs.getString(5));
            employeeInfo.setDateOfBirth(rs.getString(6));
            employeeInfo.setSalary(rs.getDouble(7));
            employees.add(employeeInfo);
        }
        return employees;
    }

    public void deleteById(int id) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("delete from EmployeeInfo where Id=?");
        statement.setInt(1, id);
        statement.execute();
    }

    public void addEmployee(EmployeeInfo newEmployee) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO EmployeeInfo (First_Name, Last_Name, Tel, Tel_Emergency, Date_Of_Birth, Salary) VALUES (?, ?, ?, ?, DATE ?, ?)");
        statement.setString(1, newEmployee.getFirstName());
        statement.setString(2, newEmployee.getLastName());
        statement.setString(3, newEmployee.getTel());
        statement.setString(4, newEmployee.getTelEmergency());
        statement.setString(5, newEmployee.getDateOfBirth());
        statement.setDouble(6, newEmployee.getSalary());
        statement.execute();
    }

    public void updateEmployee(int id, EmployeeInfo newEmployee) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("update EmployeeInfo set First_Name = ?, Last_Name = ?, Tel = ?, Tel_Emergency = ?, Date_Of_Birth = DATE ?, Salary = ? where Id = ? ");
        statement.setString(1, newEmployee.getFirstName());
        statement.setString(2, newEmployee.getLastName());
        statement.setString(3, newEmployee.getTel());
        statement.setString(4, newEmployee.getTelEmergency());
        statement.setString(5, newEmployee.getDateOfBirth());
        statement.setDouble(6, newEmployee.getSalary());
        statement.setInt(7, id);
        statement.execute();
    }

    public List<EmployeeInfo> lookForName(String extract) throws SQLException {
        String temp = "%".concat(extract).concat("%");
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from EmployeeInfo where First_Name like ? or Last_Name like ?");
        statement.setString(1, temp);
        statement.setString(2, temp);
        ResultSet rs = statement.executeQuery();

        return parseEmployees(rs);
    }

    public List<EmployeeInfo> upcomingBirthdays() throws SQLException {
        List<EmployeeInfo> results = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            String date = LocalDate.now().plusDays(i).toString();
            String temp = "%".concat(date.substring(4));
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from EmployeeInfo where Date_of_birth like ?");
            statement.setString(1, temp);
            ResultSet rs = statement.executeQuery();
            results.addAll(parseEmployees(rs));
        }
        return results;
    }

    public List<EmployeeInfo> todayBirthday() throws SQLException {
        String date = LocalDate.now().toString();
        String temp = "%".concat(date.substring(4));
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from EmployeeInfo where Date_of_birth like ?");
        statement.setString(1, temp);
        ResultSet rs = statement.executeQuery();

        return parseEmployees(rs);
    }
}
