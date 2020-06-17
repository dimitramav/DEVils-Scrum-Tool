package scrumtool.data.entities;

import scrumtool.model.PBI;
import scrumtool.model.Sprint;

import java.sql.SQLException;
import java.util.List;


public interface SprintInterface {

    public int createNewSprint(Sprint sprint);
    public Sprint updateCurrentSprint(Sprint sprint);
    public List<Sprint> getAllProjectSprints(int projectId);
    public Sprint getProjectSprint(int projectId, int sprintId);
    public Sprint getProjectCurrentSprint(int projectId);
    public void updateSprintId(List<PBI> pbis);
    public Sprint deleteCurrentSprint(Sprint sprint) throws SQLException;
}
