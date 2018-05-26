package ys09.data;

import org.springframework.jdbc.core.RowMapper;
import ys09.model.Epic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EpicRowMapper implements RowMapper<Epic> {
    @Override
    public Epic mapRow(ResultSet rs, int rowNum) throws SQLException {

        int idPBI = rs.getInt("idPBI");
        String title = rs.getString("title");
        String description = rs.getString("description");
        int order = rs.getInt("order");
        Boolean isEpic = rs.getBoolean("isEpic");
        int Project_id = rs.getInt("Project_id");
        int Epic_id = rs.getInt("Epic_id");
        int Sprint_id = rs.getInt("Sprint_id");

        return new Epic(idPBI, title, description, order, isEpic, Project_id, Epic_id, Sprint_id);
    }
}
