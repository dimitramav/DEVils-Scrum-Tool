package devils.scrumtool.services;

import devils.scrumtool.entities.User;
import devils.scrumtool.entities.User_has_Project;
import devils.scrumtool.entities.User_has_Project.UserHasProjectId;
import devils.scrumtool.models.TeamMember;
import devils.scrumtool.repositories.UserRepository;
import devils.scrumtool.repositories.User_has_ProjectRepository;
// Java libraries
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamMemberService {

    @Autowired private User_has_ProjectRepository userHasProjectRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private UserService userService;

    // Methods
    public User_has_Project getRelationByIds(Integer userId, Integer projectId) throws Exception {
        Optional<User_has_Project> relation =
                userHasProjectRepository.findByUserIdAndProjectId(userId, projectId);
        if (!relation.isPresent()) {
            throw new Exception(
                    "Project with id: " + projectId + " has no user with id: " + userId);
        }
        return relation.get();
    }

    public List<User_has_Project> getRelationsByUserId(Integer userId) {
        return userHasProjectRepository.findByUserId(userId);
    }

    public List<TeamMember> getProjectTeam(Integer projectId) throws Exception {
        // Retrieve team members
        List<User> teamUsers = userRepository.findByProjects_ProjectId(projectId);
        // Create the team (list of members)
        List<TeamMember> teamMembers = new ArrayList<TeamMember>();
        for (int i = 0; i < teamUsers.size(); i++) {
            // Find each member's role in project
            User_has_Project relation = this.getRelationByIds(teamUsers.get(i).getId(), projectId);
            teamMembers.add(new TeamMember(teamUsers.get(i), projectId, relation.getRole()));
        }
        return teamMembers;
    }

    @Transactional
    public void addMemberIntoProject(String role, Integer userId, Integer projectId)
            throws Exception {
        // Add a new relation in User_has_Project
        UserHasProjectId relationId = new UserHasProjectId(userId, projectId);
        User_has_Project relation = new User_has_Project(relationId, role);
        userHasProjectRepository.save(relation);
        // Update the user's num_projects
        User userToUpdate = userService.getUserById(userId);
        userToUpdate.setNumProjects(userToUpdate.getNumProjects() + 1);
        userRepository.save(userToUpdate);
    }

    @Transactional
    public void deleteMemberFromProject(Integer userId, Integer projectId) throws Exception {
        // Delete relation by both user and project ids
        userHasProjectRepository.deleteByUserIdAndProjectId(userId, projectId);
        // Update the user's num_projects (remove 1)
        User userToUpdate = userService.getUserById(userId);
        userToUpdate.setNumProjects(userToUpdate.getNumProjects() - 1);
        userRepository.save(userToUpdate);
    }
}
