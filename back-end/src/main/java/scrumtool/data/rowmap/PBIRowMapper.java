package scrumtool.data.rowmap;

import scrumtool.model.PBI;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PBIRowMapper implements RowMapper<PBI> {

    @Override
    public PBI mapRow(ResultSet rs, int rowNum) throws SQLException {

        int idPBI = rs.getInt("idPBI");
        String title = rs.getString("title");
        String description = rs.getString("description");
        int priority = rs.getInt("priority");
        Boolean isEpic = rs.getBoolean("isEpic");
        int Project_id = rs.getInt("Project_id");
        int Epic_id = rs.getInt("Epic_id");
        int Sprint_id = rs.getInt("Sprint_id");

        return new PBI(idPBI, title, description, priority, isEpic, Project_id, Epic_id, Sprint_id);
    }
}
