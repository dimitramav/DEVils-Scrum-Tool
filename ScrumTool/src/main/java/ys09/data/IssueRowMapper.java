package ys09.data;

import ys09.model.Issue;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class IssueRowMapper implements RowMapper<Issue> {

    @Override
    public Issue mapRow(ResultSet rs, int rowNum) throws SQLException {

        Issue issue = new Issue();

        issue.setId(rs.getInt("idIssue"));
        issue.setDescription(rs.getString("description"));
        issue.setState(rs.getInt("state"));
        issue.setTask_id(rs.getInt("Task_id"));

        return issue;
    }
}