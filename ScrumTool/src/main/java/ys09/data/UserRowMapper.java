package ys09.data;
import ys09.model.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setIdUser(rs.getInt("idUser"));
        user.setMail(rs.getString("mail"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setPassword(rs.getString("password"));
        user.setCountry(rs.getString("country"));
        user.setBio(rs.getString("bio"));
        user.setDescription(rs.getString("description"));
        user.setCompany(rs.getString("company"));
        user.setJob(rs.getString("job"));
        user.setPhoto(rs.getString("photo"));
        user.setNumOfProjects(rs.getInt("numProjects"));
        user.setIsAdmin(rs.getInt("isAdmin"));

        return user;
    }
}
