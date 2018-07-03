package ys09.data;

import ys09.model.PBI;
import ys09.model.Sprint;

import java.util.List;

public interface SprintInterface {
    public int createNewSprint(Sprint sprint);
    public Sprint getProjectCurrentSprint(int projectId);
    public void updateSprintId(List<PBI> pbis);
}
