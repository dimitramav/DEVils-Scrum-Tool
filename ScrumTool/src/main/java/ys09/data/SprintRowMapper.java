package ys09.data;

import ys09.model.Sprint;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SprintRowMapper implements RowMapper<Sprint> {

    @Override
    public Sprint mapRow(ResultSet rs, int rowNum) throws SQLException {

        Sprint sprint = new Sprint();

        sprint.setIdSprint(rs.getInt("idSprint"));
        sprint.setDeadlineDate(rs.getDate("deadlineDate"));
        sprint.setGoal(rs.getString("goal"));
        sprint.setPlan(rs.getString("plan"));
        sprint.setCurrent(rs.getBoolean("isCurrent"));
        sprint.setNumSprint(rs.getInt("numSprint"));
        sprint.setProject_id(rs.getInt("Project_id"));

        return sprint;
    }
}