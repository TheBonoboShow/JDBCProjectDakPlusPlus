package spacedandy.services;

import spacedandy.data.ProjectDAO;
import spacedandy.model.HardWork;
import spacedandy.model.Profitability;
import spacedandy.model.Project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectService {
    private ProjectDAO projectDAO = new ProjectDAO();

    public List<Project> showStartingToday() throws SQLException {
        return projectDAO.showStartingToday();
    }

    public List<Project> getAllProjects() throws SQLException {
        return projectDAO.getAllProjects();
    }

    public void addProject(Project project) throws SQLException {
        projectDAO.addProject(project);
    }

    public Optional<Project> getProjectById(int id) throws SQLException {
        return projectDAO.getProjectById(id);
    }

    public void deleteProject(int id) throws SQLException {
        projectDAO.deleteProject(id);
    }

    public List<Project> getOngoingProjects() throws SQLException {
        return projectDAO.getOngoingProjects();
    }

    public String getStartDateById(int id) throws SQLException {
        Optional<Project> optionalProject = projectDAO.getProjectById(id);
        if (optionalProject.isPresent()) {
            return optionalProject.get().getStartDate();
        }
        return "";
    }

    public List<HardWork> getTopProductiveById(int id) throws SQLException {
        return projectDAO.getTopProductiveById(id);
    }

    public List<Profitability> getProfitProjects() throws SQLException {
        return projectDAO.getProfitProjects();
    }
}
