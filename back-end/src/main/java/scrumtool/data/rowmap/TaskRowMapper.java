package scrumtool.data.rowmap;

import scrumtool.model.Task;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {

        Task task = new Task();

        task.setId(rs.getInt("idTask"));
        task.setDescription(rs.getString("description"));
        task.setState(rs.getInt("state"));
        task.setPBI_id(rs.getInt("PBI_id"));

        return task;
    }
}
