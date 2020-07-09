package spacedandy.data;

import spacedandy.model.Project;
import spacedandy.model.WorkDone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkDoneDAO {

    public List<WorkDone> getAllWorkDone() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from WorkDone");
        return parseWorkDone(rs);
    }

    private List<WorkDone> parseWorkDone (ResultSet rs) throws SQLException {
        List<WorkDone> workDoneList = new ArrayList<>();
        while (rs.next()){
            WorkDone workDone = new WorkDone();
            workDone.setEmployeeId(rs.getInt(1));
            workDone.setProjectId(rs.getInt(2));
            workDone.setDate(rs.getString(3));
            workDone.setHoursWorked(rs.getDouble(4));
            workDone.setRemarks(rs.getString(5));
            workDoneList.add(workDone);
        }
        return workDoneList;
    }

    public void addWorkDone(WorkDone workDone) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO WorkDone VALUES (?, ?, DATE ?, ?, ?)");
        statement.setInt(1,workDone.getEmployeeId());
        statement.setInt(2,workDone.getProjectId());
        statement.setString(3, workDone.getDate());
        statement.setDouble(4,workDone.getHoursWorked());
        statement.setString(5,workDone.getRemarks());
        statement.execute();
    }

    public Optional<WorkDone> getEntry(int id1, int id2, String date) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from WorkDone where Employee_Id=? and Project_Id = ? and Date = DATE ? ");
        statement.setInt(1, id1);
        statement.setInt(2, id2);
        statement.setString(3, date);
        ResultSet rs = statement.executeQuery();

        List<WorkDone> workDone = parseWorkDone(rs);
        if (workDone.size() == 1) return Optional.of(workDone.get(0));
        return Optional.empty();
    }

    public void deleteHours(int id1, int id2, String date) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("delete from WorkDone where Employee_Id=? and Project_Id = ? and Date = DATE ? ");
        statement.setInt(1, id1);
        statement.setInt(2, id2);
        statement.setString(3, date);
        statement.execute();
    }

    public void updateWorkDone(int id1, int id2, String date, WorkDone workDone) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("update WorkDone set Employee_Id = ?, Project_Id = ?, Date = DATE ?, Hours_Worked = ?, Remarks = ? where Employee_Id=? and Project_Id = ? and Date = DATE ? ");
        statement.setInt(1, workDone.getEmployeeId());
        statement.setInt(2, workDone.getProjectId());
        statement.setString(3, workDone.getDate());
        statement.setDouble(4, workDone.getHoursWorked());
        statement.setString(5, workDone.getRemarks());
        statement.setInt(6, id1);
        statement.setInt(7, id2);
        statement.setString(8, date);
        statement.execute();
    }
}
