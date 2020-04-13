package scrumtool.data.rowmap;

import scrumtool.model.Team;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TeamRowMapper implements RowMapper<Team> {

    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {

        Team team = new Team();

        team.setIdUser(rs.getInt("idUser"));
        team.setMail(rs.getString("mail"));
        team.setFirstname(rs.getString("firstname"));
        team.setLastname(rs.getString("lastname"));
        team.setPhoto(rs.getString("photo"));

        return team;
    }
}
