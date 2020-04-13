package scrumtool.data.rowmap;

import scrumtool.model.Notification;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class NotificationRowMapper implements RowMapper<Notification> {

    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {

        Notification notification = new Notification();

        notification.setIdNotification(rs.getInt("idNotification"));
        notification.setProject_id(rs.getInt("Project_Id"));
        notification.setProjectTitle(rs.getString("projectTitle"));
        notification.setRole(rs.getString("role"));
        notification.setFromUsername(rs.getString("FromUsername"));
        notification.setToUserEmail(rs.getString("ToUserEmail"));
        notification.setType(rs.getString("type"));
        notification.setText(rs.getString("text"));

        return notification;
    }
}
