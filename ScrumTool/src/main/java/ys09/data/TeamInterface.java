package ys09.data;

import ys09.model.Team;

import java.util.List;

public interface TeamInterface {
    // get all the team members
    public List<Team> getTeamMembers(int projectId);
    public Team insertNewMember(Team member, int projectId);
}
