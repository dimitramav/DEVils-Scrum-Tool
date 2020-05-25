package scrumtool.data.rowmap;

import scrumtool.model.Project_has_User;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProjecthasUserRowMapper implements RowMapper<Project_has_User>  {

	@Override
    public Project_has_User mapRow(ResultSet rs, int rowNum) throws SQLException {

        int Project_id = rs.getInt("Project_id");
        int User_id = rs.getInt("User_id");
        String role = rs.getString("role");

        return new Project_has_User(Project_id, User_id, role);
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
