package devils.scrumtool.services;

import devils.scrumtool.entities.Project;
import devils.scrumtool.entities.User;
import devils.scrumtool.entities.User_has_Project;
import devils.scrumtool.entities.User_has_Project.UserHasProjectId;
import devils.scrumtool.models.ProjectOverview;
import devils.scrumtool.models.TeamMember;
import devils.scrumtool.repositories.ProjectRepository;
import devils.scrumtool.repositories.UserRepository;
import devils.scrumtool.repositories.User_has_ProjectRepository;
// Java libraries
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    @Autowired private ProjectRepository projectRepository;

    @Autowired private User_has_ProjectRepository userHasProjectRepository;

    @Autowired private UserRepository userRepository;

    public List<Project> getLimitedUserProjects(
            Integer userId, Boolean isDone, Integer recordsPerPage, Integer currentPage) {
        // Calculate how many and which projects will be retrieved
        Sort sortType = Sort.by("deadlineDate").ascending();
        Pageable paging = PageRequest.of((currentPage - 1), recordsPerPage, sortType);
        // Retreive the right page and return the list of projects
        return projectRepository.findByUsers_UserIdAndIsDone(userId, isDone, paging);
    }

    @Transactional
    public Project createProject(Project newProject, Integer userId) throws Exception {
        // Save new project
        Project insertedProject = projectRepository.save(newProject);
        // Update the user_has_project realtion
        UserHasProjectId relationId = new UserHasProjectId(userId, insertedProject.getId());
        User_has_Project relation = new User_has_Project(relationId, "Product Owner");
        userHasProjectRepository.save(relation);
        // Update the user's num_projects
        Optional<User> dbUser = userRepository.findById(userId);
        if (!dbUser.isPresent()) {
            throw new Exception("User with id: " + userId + " not found!");
        }
        User userToUpdate = dbUser.get();
        userToUpdate.setNumProjects(userToUpdate.getNumProjects() + 1);
        userRepository.save(userToUpdate);
        // Finally return project
        return insertedProject;
    }

    public ProjectOverview getProjectCurrentSprintOverview(Integer projectId) throws Exception {
        // Retrieve current project
        Optional<Project> dbProject = projectRepository.findById(projectId);
        if (!dbProject.isPresent()) {
            throw new Exception("Project with id: " + projectId + " not found!");
        }
        Project projectItem = dbProject.get();
        // Create the projectOverview item
        ProjectOverview projectOverviewItem = new ProjectOverview();
        projectOverviewItem.setProject(projectItem);
        // Finally return project overview
        return projectOverviewItem;
    }

    @Transactional
    public List<TeamMember> getProjectTeam(Integer projectId) throws Exception {
        // Retrieve team members
        List<User> teamUsers = userRepository.findByProjects_ProjectId(projectId);
        // Create the team (list of members)
        List<TeamMember> teamMembers = new ArrayList<TeamMember>();
        for (int i = 0; i < teamUsers.size(); i++) {
            // Find each member's role in project
            int currentUserId = teamUsers.get(i).getId();
            Optional<User_has_Project> relation =
                    userHasProjectRepository.findByUserIdAndProjectId(currentUserId, projectId);
            if (!relation.isPresent()) {
                throw new Exception(
                        "Project with id: " + projectId + " has no user with id: " + currentUserId);
            }
            teamMembers.add(new TeamMember(teamUsers.get(i), projectId, relation.get().getRole()));
        }
        return teamMembers;
    }

    @Transactional
    public void deleteProjectFull(Integer projectId) {
        userHasProjectRepository.deleteByProjectId(projectId);
        projectRepository.deleteById(projectId);
    }
}
