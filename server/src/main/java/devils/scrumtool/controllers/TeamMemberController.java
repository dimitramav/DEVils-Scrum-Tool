package devils.scrumtool.controllers;

import devils.scrumtool.models.TeamMember;
import devils.scrumtool.services.TeamMemberService;
// Java libraries
import java.util.List;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Team Member API (user_has_project relation)
@RestController
public class TeamMemberController {

    @Autowired private TeamMemberService teamMemberService;

    @GetMapping("/users/{userId}/projects/{projectId}/members")
    public List<TeamMember> getProjectMembers(
            @PathVariable Integer userId, @PathVariable Integer projectId) throws Exception {
        return teamMemberService.getProjectTeam(projectId);
    }

    @PostMapping("/users/{userId}/projects/{projectId}/members")
    public void insertNewMemberIntoProject(
            @RequestBody String role, @PathVariable Integer userId, @PathVariable Integer projectId)
            throws Exception {
        teamMemberService.addMemberIntoProject(role, userId, projectId);
    }

    @DeleteMapping("/users/{userId}/projects/{projectId}/members")
    public void deleteTeamMember(@PathVariable Integer userId, @PathVariable Integer projectId)
            throws Exception {
        teamMemberService.deleteMemberFromProject(userId, projectId);
    }
}
