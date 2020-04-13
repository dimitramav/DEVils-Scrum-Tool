package scrumtool.data.entities;

import scrumtool.model.Team;

import java.util.List;


public interface TeamInterface {
    // get all the team members
    public List<Team> getTeamMembers(int projectId);
    public Team insertNewMember(Team member, int projectId);
}
