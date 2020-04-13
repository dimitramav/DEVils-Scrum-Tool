package scrumtool.data.entities;

import scrumtool.model.Team;
import scrumtool.model.User;
import scrumtool.data.DataAccess;
import scrumtool.data.rowmap.TeamRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.ResultSet;
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
        String query1 = "select * from User where mail = ?;";
        String query2 = "insert into Project_has_User (Project_id, User_id, role) values (?, ?, ?);";
        String query3 = "update User set numProjects = numProjects + 1 where idUser = ?;";

        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        Connection dbConnection = null;

        try {
            dbConnection = jdbcTemplate.getDataSource().getConnection();
            dbConnection.setAutoCommit(false);

            // Take the user's id
            statement1 = dbConnection.prepareStatement(query1);
            statement1.setString(1, member.getMail());
            ResultSet rs = statement1.executeQuery();
            int userId = 0;
            while (rs.next()){
                userId = rs.getInt("idUser");
                member.setIdUser(userId);
                member.setFirstname(rs.getString("firstname"));
                member.setLastname(rs.getString("lastname"));
                member.setPhoto(rs.getString("photo"));
            }
            // Update Project_has_User table
            statement2 = dbConnection.prepareStatement(query2);
            statement2.setInt(1, projectId);
            statement2.setInt(2, userId);
            statement2.setString(3, member.getRole());
            statement2.executeUpdate();

            // Update User's number of Projects
            statement3 = dbConnection.prepareStatement(query3);
            statement3.setInt(1, userId);
            statement3.executeUpdate();

            dbConnection.commit();      // Commit manually for single transaction
            return member;
        }
        catch (SQLException e) {      // Error in one of the insert statements
            e.printStackTrace();
            try { dbConnection.rollback(); }
            catch (SQLException e1) { e1.printStackTrace(); }
            return null;
        // Finally close statements and connection
        } finally {
            if (statement1 != null) {
                try { statement1.close(); }
                catch (SQLException e) { e.printStackTrace(); }
            }
            if (statement2 != null) {
                try { statement2.close(); }
                catch (SQLException e) { e.printStackTrace(); }
            }
            if (statement3 != null) {
                try { statement3.close(); }
                catch (SQLException e) { e.printStackTrace(); }
            }
            if (dbConnection != null) {
                try { dbConnection.close(); }
                catch (SQLException e) { e.printStackTrace(); }
            }
        }

        /*jdbcTemplate.update(new PreparedStatementCreator() {
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
        return member;*/
    }

}
