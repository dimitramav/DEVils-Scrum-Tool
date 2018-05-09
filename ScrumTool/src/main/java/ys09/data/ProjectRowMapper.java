package ys09.data;

import org.springframework.jdbc.core.RowMapper;
import ys09.model.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


class ProjectRowMapper implements RowMapper<Project>  {

	@Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {

        int id = rs.getInt("idProject");
        String title = rs.getString("title");
        boolean isDone = rs.getBoolean("isDone");
        Date deadlineDate = rs.getDate("deadlineDate");

        return new Project(id, title, isDone, deadlineDate);
    }
}
