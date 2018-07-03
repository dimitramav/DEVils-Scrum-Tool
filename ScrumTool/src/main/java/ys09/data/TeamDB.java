package ys09.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import ys09.model.Team;
import ys09.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TeamDB implements TeamInterface {

    public List<Team> getTeamMembers(int projectId) {

        DataAccess dataAccess = new DataAccess();

        JdbcTemplate jdbcTemplate = dataAccess.getInstance();

        // Find the Members of current project
        String query = "select idUser, mail, firstname, lastname, photo from User where idUser in (select User_id from Project_has_User where Project_id = ?);";
        return jdbcTemplate.query(query, new Object[]{projectId}, new TeamRowMapper());
    }

    public Team insertNewMember(Team member, int projectId) {

        DataAccess dataAccess = new DataAccess();

        JdbcTemplate jdbcTemplate = dataAccess.getInstance();
        // Insert a new member into project (update the Project_has_User table)
        String query = "insert into Project_has_User (Project_id, User_id, role) values (?, ?, ?);";

        // Take the user's id
        String queryInfo = "select * from User where mail = ?;";
        User userInfo = jdbcTemplate.queryForObject(queryInfo, new Object[]{member.getMail()}, new UserRowMapper());
        int userId = userInfo.getId();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query);
                statement.setInt(1, projectId);
                statement.setInt(2, userId);
                statement.setString(3, member.getRole());;
                return statement;
            }
        });
        // Return member information
        member.setIdUser(userId);
        member.setFirstname(userInfo.getFirstName());
        member.setLastname(userInfo.getLastName());
        member.setPhoto(userInfo.getPhoto());
        return member;
    }

}
