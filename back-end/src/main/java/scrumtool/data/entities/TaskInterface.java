package scrumtool.data.entities;

import scrumtool.model.Task;
import java.sql.SQLException;


public interface TaskInterface {

    public Task insert(Task task);
    public Task update(Task task);
    public int delete(Task task) throws SQLException;
}
