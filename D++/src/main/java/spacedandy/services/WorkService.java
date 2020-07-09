package spacedandy.services;

import spacedandy.data.WorkDoneDAO;
import spacedandy.model.WorkDone;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class WorkService {
    private WorkDoneDAO workDoneDAO = new WorkDoneDAO();

    public List<WorkDone> getAllWorkDone() throws SQLException {
        return workDoneDAO.getAllWorkDone();
    }

    public void addWorkDone(WorkDone workDone) throws SQLException {
        workDoneDAO.addWorkDone(workDone);
    }

    public void deleteHours(int id1, int id2, String date) throws SQLException {
        workDoneDAO.deleteHours(id1, id2, date);
    }

    public Optional<WorkDone> getEntry(int id1, int id2, String date) throws SQLException {
        return workDoneDAO.getEntry(id1, id2, date);
    }

    public void updateWorkDone(int id1, int id2, String date, WorkDone workDone) throws SQLException {
        workDoneDAO.updateWorkDone(id1, id2, date, workDone);
    }
}
