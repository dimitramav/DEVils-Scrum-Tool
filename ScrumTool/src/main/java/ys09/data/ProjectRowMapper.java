package ys09.data;

import org.springframework.jdbc.core.RowMapper;
import ys09.model.Project;

import java.sql.ResultSet;
import java.sql.SQLException;

class ProjectRowMapper implements RowMapper<Project>  {

	@Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {

        long id = rs.getLong("idProject");
        String title = rs.getString("title");
        int isDone = rs.getInt("isDone");
        String deadlineDate = rs.getString("deadlineDate");

        return new Project(id, title, isDone, deadlineDate);
    }
}
