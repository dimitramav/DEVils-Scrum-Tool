package scrumtool.data.entities;

import scrumtool.model.PBI;
import scrumtool.model.Sprint;
import scrumtool.data.DataAccess;
import scrumtool.data.rowmap.SprintRowMapper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;



public class SprintDB implements SprintInterface {
    // Create new sprint
    // Insert User
    public int createNewSprint(Sprint sprint) {
        // Update last current sprint
        DataAccess dataAccess = new DataAccess();
        JdbcTemplate jdbcTemplate = dataAccess.getInstance();

        String query1 = "UPDATE Sprint SET isCurrent = FALSE WHERE Project_id = ? AND isCurrent = TRUE";
        jdbcTemplate.update(query1, new Object[]{sprint.getProject_id()});

        String query = "INSERT INTO Sprint(deadlineDate, goal, plan, isCurrent, numSprint, Project_id) VALUES (?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        java.sql.Date sqlDate = new java.sql.Date(sprint.getDeadlineDate().getTime());

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setDate(1, sqlDate);
                statement.setString(2, sprint.getGoal());
                statement.setString(3, sprint.getPlan());
                statement.setBoolean(4, sprint.getCurrent());
                statement.setInt(5, sprint.getNumSprint());
                statement.setInt(6, sprint.getProject_id());
                return statement;
            }
        }, keyHolder);
        // Return the new generated id for user
        return keyHolder.getKey().intValue();
    }

    // Update current sprint's info
    public Sprint updateCurrentSprint(Sprint sprint) {
        // Update last current sprint
        DataAccess dataAccess = new DataAccess();
        JdbcTemplate jdbcTemplate = dataAccess.getInstance();

        String query = "update Sprint set goal = ?, plan = ?, deadlineDate = ? where idSprint = ?;";

        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    java.sql.Date sqlDate = new java.sql.Date(sprint.getDeadlineDate().getTime());
                    statement.setString(1, sprint.getGoal());
                    statement.setString(2, sprint.getPlan());
                    statement.setDate(3, sqlDate);
                    statement.setInt(4, sprint.getIdSprint());
                    return statement;
                }
            });
            return sprint;
        // Error in update of jdbcTemplate
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Sprint getProjectCurrentSprint(int projectId) {

        DataAccess dataAccess = new DataAccess();

        JdbcTemplate jdbcTemplate = dataAccess.getInstance();
        // Get the current sprint of a project
        String query = "select * from Sprint where Project_id = ? and isCurrent = TRUE;";
        try {
            Sprint sprintItem = jdbcTemplate.queryForObject(query, new Object[]{projectId}, new SprintRowMapper());
            return sprintItem;
        } catch (EmptyResultDataAccessException e) {
            Sprint sprintItem = new Sprint();
            return sprintItem;
        }
    }

    // Update PBI's Sprint_id field
    public void updateSprintId(List<PBI> pbis) {

        DataAccess dataAccess = new DataAccess();

        JdbcTemplate jdbcTemplate = dataAccess.getInstance();

        String sql = "UPDATE PBI SET Sprint_id = ? WHERE idPBI = ?";
        //JdbcTemplate template = new JdbcTemplate(dataSource);
        //System.out.println("updateSprint");

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PBI singlePbi = pbis.get(i);
                if (singlePbi.getSprint_id() == -1) {
                    ps.setNull(1, java.sql.Types.INTEGER);
                }
                else ps.setInt(1, singlePbi.getSprint_id());
                ps.setInt(2, singlePbi.getIdPBI());
            }
            @Override
            public int getBatchSize() {
                return pbis.size();
            }
        });
    }

    // Delete current sprint
    public Sprint deleteCurrentSprint(Sprint sprint) throws SQLException {

        DataAccess dataAccess = new DataAccess();
        JdbcTemplate jdbcTemplate = dataAccess.getInstance();
        DataSource dataSource = dataAccess.getDataSource();

        // Update pbis attached to this sprint
        String sql = "UPDATE PBI SET Sprint_id = ? WHERE Sprint_id = ?";
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setNull(1, java.sql.Types.INTEGER);
                    statement.setInt(2, sprint.getIdSprint());
                    return statement;
                }
            });
            // Check how many sprints exist in this project
            String query = "select count(*) from Sprint where Project_id = ?;";
            int numOfSprintsInProject = jdbcTemplate.queryForObject(query, new Object[]{sprint.getProject_id()}, Integer.class);
            System.out.println(numOfSprintsInProject);
            // Delete project
            String query1 = "delete from Sprint where idSprint = ?;";
            String query2 = "select max(numSprint) from Sprint where Project_id = ?;";
            String query3 = "update Sprint set isCurrent = TRUE where numSprint = ? and Project_id = ?;";

            PreparedStatement statement1 = null;
            PreparedStatement statement2 = null;
            PreparedStatement statement3 = null;
            Connection dbConnection = null;
            // For 2 or more queries, transactions must be placed
            try {
                dbConnection = dataSource.getConnection();
                dbConnection.setAutoCommit(false);
                // First delete the current sprint
                statement1 = dbConnection.prepareStatement(query1);
                statement1.setInt(1, sprint.getIdSprint());
                statement1.executeUpdate();
                // If there are more sprints, check which is the latest
                if (numOfSprintsInProject > 1) {
                    statement2 = dbConnection.prepareStatement(query2);
                    statement2.setInt(1, sprint.getProject_id());
                    ResultSet rs = statement2.executeQuery();
                    int maxNumSprint = -1;
                    while (rs.next()) {
                        maxNumSprint = rs.getInt("max(numSprint)");
                    }
                    rs.close();
                    System.out.println(maxNumSprint);
                    if (maxNumSprint == -1) return null;
                    // Set the latest sprint as current
                    statement3 = dbConnection.prepareStatement(query3);
                    statement3.setInt(1, maxNumSprint);
                    statement3.setInt(2, sprint.getProject_id());
                    statement3.executeUpdate();
                }
                dbConnection.commit();      //Commit manually for single transaction
            // Error in one of the insert statements
            } catch (SQLException e) {
                e.printStackTrace();
                dbConnection.rollback();
                return null;
            // Finally close statements and connection
            } finally {
                if (statement1 != null) {
                    statement1.close();
                }
                if (statement2 != null) {
                    statement2.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            }
            // Return sprint
            return sprint;
        // Error in update of jdbcTemplate
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
