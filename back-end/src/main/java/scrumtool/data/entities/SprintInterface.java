package scrumtool.data.entities;

import scrumtool.model.PBI;
import scrumtool.model.Sprint;

import java.sql.SQLException;
import java.util.List;


public interface SprintInterface {

    public int createNewSprint(Sprint sprint);
    public Sprint updateCurrentSprint(Sprint sprint);
    public Sprint getProjectCurrentSprint(int projectId);
    public void updateSprintId(List<PBI> pbis);
    public Sprint deleteCurrentSprint(Sprint sprint) throws SQLException;
}
