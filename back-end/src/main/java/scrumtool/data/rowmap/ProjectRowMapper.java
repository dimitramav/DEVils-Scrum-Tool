package scrumtool.data.rowmap;

import scrumtool.model.Project;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class ProjectRowMapper implements RowMapper<Project>  {

	@Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {

        int id = rs.getInt("idProject");
        String title = rs.getString("title");
        boolean isDone = rs.getBoolean("isDone");
        Date deadlineDate = rs.getDate("deadlineDate");

        return new Project(id, title, isDone, deadlineDate);
    }

    /*
    public List<Project> mapProjects(ResultSet rs) throws SQLException {

    	List<Project> projects = new ArrayList<>();

        while(rs.next()) {
            Project project = new Project(
                rs.getInt("idProject"),
                rs.getString("title"),
                rs.getBoolean("isDone"),
                rs.getDate("deadlineDate")
            );
            projects.add(project);
        }
        return projects;
    }*/
}
