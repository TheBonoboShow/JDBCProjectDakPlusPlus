package spacedandy.data;

import spacedandy.model.EmployeeInfo;
import spacedandy.model.HardWork;
import spacedandy.model.Profitability;
import spacedandy.model.Project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectDAO {
    public List<Project> showStartingToday() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from Projects where Start_Date = ? ");
        statement.setString(1, LocalDate.now().toString());
        ResultSet rs = statement.executeQuery();
        return parseProjects(rs);
    }

    private List<Project> parseProjects (ResultSet rs) throws SQLException{
        List<Project> projects = new ArrayList<>();
        while (rs.next()){
            Project project = new Project();
            project.setId(rs.getInt(1));
            project.setStartDate(rs.getString(2));
            project.setDescription(rs.getString(3));
            project.setPrice(rs.getDouble(4));
            project.setEndDate(rs.getString(5));
            projects.add(project);
        }
        return projects;
    }

    public List<Project> getAllProjects() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from Projects");
        return parseProjects(rs);
    }

    public void addProject(Project project) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Projects (Start_Date, Description, Price, End_Date) VALUES (DATE ?, ?, ?, DATE ?)");
        statement.setString(1, project.getStartDate());
        statement.setString(2, project.getDescription());
        statement.setDouble(3, project.getPrice());
        statement.setString(4, project.getEndDate());
        statement.execute();
    }

    public Optional<Project> getProjectById(int id) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from Projects where Id=?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        List<Project> project = parseProjects(rs);
        if (project.size() == 1) return Optional.of(project.get(0));
        return Optional.empty();
    }

    public void deleteProject(int id) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("delete from Projects where Id=?");
        statement.setInt(1, id);
        statement.execute();
    }

    public List<Project> getOngoingProjects() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from Projects where DATE ? between Start_Date and End_Date");
        statement.setString(1, LocalDate.now().toString());
        ResultSet rs = statement.executeQuery();
        return parseProjects(rs);
    }

    public List<HardWork> getTopProductiveById(int id) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("select E.First_Name, E.Last_name, Res.Total_Hours_Worked from EmployeeInfo as E inner join (select Employee_Id, sum(Hours_Worked) as Total_Hours_worked from WorkDone where Project_id = ? group by Employee_id) as Res on E.Id = Res.Employee_Id order by Total_Hours_Worked DESC limit 3");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        List<HardWork> hardWorks = new ArrayList<>();
        while (rs.next()){
            HardWork hardWork = new HardWork();
            hardWork.setFirstName(rs.getString(1));
            hardWork.setLastName(rs.getString(2));
            hardWork.setHoursWorked(rs.getDouble(3));
            hardWorks.add(hardWork);
        }
        return hardWorks;
    }

    public List<Profitability> getProfitProjects() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select Project_Id, (Price-Cost) as Profit from\n" +
                "(select Project_id, sum(Cost) as Cost, Price from Projects as c\n" +
                "inner join (select Employee_Id, Project_Id, Salary/22/8*Hours_Total as Cost from EmployeeInfo as a\n" +
                "inner join (select Employee_id, Project_id, sum(Hours_Worked) as Hours_Total from WorkDone\n" +
                "group by Employee_Id, Project_Id) as b\n" +
                "on a.Id = b.Employee_Id) as d\n" +
                "on c.Id = d.Project_Id\n" +
                "group by Project_Id) as e;");
        List<Profitability> profitability = new ArrayList<>();
        while (rs.next()){
            Profitability profit = new Profitability();
            profit.setId(rs.getInt(1));
            profit.setProfit(rs.getDouble(2));
            profitability.add(profit);
        }
        return profitability;
    }
}
