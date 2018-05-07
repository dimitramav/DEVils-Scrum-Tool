package ys09.data;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ys09.model.Project;
import ys09.model.User;
import ys09.model.SignIn;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.jdbc.core.PreparedStatementSetter;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataAccess {

    private static final int MAX_TOTAL_CONNECTIONS = 16;
    private static final int MAX_IDLE_CONNECTIONS   = 8;

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setup(String driverClass, String url, String user, String pass) throws SQLException {

        //initialize the data source
	    BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(driverClass);
        bds.setUrl(url);
        bds.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        bds.setMaxIdle(MAX_IDLE_CONNECTIONS);
        bds.setUsername(user);
        bds.setPassword(pass);
        bds.setValidationQuery("SELECT 1");
        bds.setTestOnBorrow(true);
        bds.setDefaultAutoCommit(true);

        //check that everything works OK
        bds.getConnection().close();

        //initialize the jdbc template utilitiy
        jdbcTemplate = new JdbcTemplate(bds);

        //keep the dataSource for the low-level manual example to function (not actually required)
        dataSource = bds;
    }

    /*public void insertProject(Project project) {
      // Insert an object to the projects array
      // Generate the id
      String sql = "INSERT INTO project " +
      "(owner_id, name, description) VALUES (?, ?, ?)";

      //jdbcTemplate = new JdbcTemplate(dataSource);

      jdbcTemplate.update(sql, new Object[] { project.getOwnerId(),
        project.getName(),project.getDescription()
      });
    }*/

    public List<Project> getProjects() {
        //TODO: Support limits SOS
        // Creates the id
        return jdbcTemplate.query("select * from Project", new ProjectRowMapper());
    }

    // Check if User exists into the database
    public boolean userExists(String mail) {
        // Query to find if user exists
        jdbcTemplate = new JdbcTemplate(dataSource);
        String query = "SELECT * FROM User WHERE mail = ?";

        try {
            User exist = jdbcTemplate.queryForObject(query, new Object[]{mail}, new UserRowMapper());
            // Exists
            return true;
        }
        catch (EmptyResultDataAccessException e) {
            // Does not exists
            return false;
        }
    }

    // Insert User
    public void insertUser(User user) {
      // Generate Random Salt and Bcrypt
      String pw_hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
      user.setPassword(pw_hash);
      user.setIsAdmin(0);
      user.setNumOfProjects(0);
      // Insert into table with jdbc template
      // Avoid SQL injections
      jdbcTemplate = new JdbcTemplate(dataSource);
      jdbcTemplate.update("INSERT INTO User (mail, firstname, lastname, password, isAdmin, numProjects) VALUES (?,?,?,?,?,?)",new Object[]{
        user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getIsAdmin(), user.getNumOfProjects()});
    }

    public String checkSignIn(SignIn signin) {
      // Query to find if user exists
      jdbcTemplate = new JdbcTemplate(dataSource);
      String query = "SELECT * FROM User WHERE mail = ?";
      String mail = signin.getEmail();
      try {
          User user = jdbcTemplate.queryForObject(query, new Object[]{mail}, new UserRowMapper());

          if (BCrypt.checkpw(signin.getPassword(), user.getPassword())){
              System.out.println("It matches");
              // If it matches return JWT token !
              // Save the token to a dictionary (user,token)
              return "OK";
          }
          else {
              System.out.println("It does not match");
              return "Wrong Password";
          }
      }
      catch (EmptyResultDataAccessException e) {
          return "Not Exists";
      }
    }

    // Check User Email and Password

    /*public List<Project> getProjectsAlt(long ownerId) {

        List<Project> projects = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection(); //borrow the connection from the pool
            ps = con.prepareStatement("select * from project where owner_id = ?");
            ps.setLong(1, ownerId);
            rs = ps.executeQuery();
            while(rs.next()) {

                Project project = new Project(
                    rs.getLong("id"),
                    rs.getLong("owner_id"),
                    rs.getString("name"),
                    rs.getString("description")
                );

                projects.add(project);
            }

            return projects;
        }
        catch (SQLException e) {
            //report the error as a runtime exception
            throw new RuntimeException(e.getMessage(), e);
        }
        finally {
            if (ps != null) {
                try {
                    ps.close(); //closes the ResultSet too
                }
                catch (Exception e) {
                    //log this (leak)
                }
            }
            if (con != null) {
                try {
                    con.close(); //return the connection to the pool
                }
                catch (Exception e) {
                    //log this (leak)
                }
            }
        }
    }*/

}
