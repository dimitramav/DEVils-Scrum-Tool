package devils.scrumtool.services;

import devils.scrumtool.entities.Project;
import devils.scrumtool.entities.Sprint;
import devils.scrumtool.entities.Task;
import devils.scrumtool.entities.User;
import devils.scrumtool.entities.User_has_Project;
import devils.scrumtool.models.ProjectOverview;
import devils.scrumtool.repositories.ProjectRepository;
import devils.scrumtool.repositories.UserRepository;
import devils.scrumtool.repositories.User_has_ProjectRepository;
// Java libraries
import java.util.List;
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired private TeamMemberService teamMemberService;

    @Autowired private SprintService sprintService;

    @Autowired private TaskService taskService;

    @Autowired private IssueService issueService;

    // Methods
    public Project getProjectById(Integer projectId) throws Exception {
        Optional<Project> dbProject = projectRepository.findById(projectId);
        if (!dbProject.isPresent()) {
            throw new Exception("Project with id: " + projectId + " not found!");
        }
        return dbProject.get();
    }

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
        // Save new project and then add the current user as a member into the project
        Project insertedProject = projectRepository.save(newProject);
        teamMemberService.addMemberIntoProject("Product Owner", userId, insertedProject.getId());
        return insertedProject;
    }

    public ProjectOverview getProjectSprintOverview(Integer projectId, Optional<Integer> sprintId)
            throws Exception {
        // Retrieve current project
        Project projectItem = this.getProjectById(projectId);
        // FInd the requested sprint (if any)
        Sprint sprintItem = new Sprint();
        if (!sprintId.isPresent()) {
            sprintItem = sprintService.getProjectCurrentSprint(projectId);
        } else { // If there is a sprintId parameter, then find sprint by its id
            sprintItem = sprintService.getSprintById(sprintId.get());
        }
        // Create the projectOverview item with the values needed
        ProjectOverview projectOverviewItem =
                new ProjectOverview(projectItem, sprintItem, 0, 0, 0, 0);
        // Find the tasks and issues belong to this sprint
        List<Task> sprintTasks = taskService.getTasksByStoryIdWithSprintId(sprintItem.getId());
        // Find how many tasks and issues are in state of todo, doing or done
        for (int i = 0; i < sprintTasks.size(); i++) {
            if (sprintTasks.get(i).getState() == 1) {
                projectOverviewItem.setTodo(projectOverviewItem.getTodo() + 1);
            } else if (sprintTasks.get(i).getState() == 2) {
                projectOverviewItem.setDoing(projectOverviewItem.getDoing() + 1);
            } else if (sprintTasks.get(i).getState() == 3) {
                projectOverviewItem.setDone(projectOverviewItem.getDone() + 1);
            }
            int numOfTaskIssues = issueService.getNumberOfTaskIssues(sprintTasks.get(i).getId());
            projectOverviewItem.addIssues(numOfTaskIssues);
        }
        // Finally return the project overview item
        return projectOverviewItem;
    }

    @Transactional
    public void deleteProjectAndRelations(Integer projectId) {
        // One less project for all users participating in it
        List<User> users = userRepository.findByProjects_ProjectId(projectId);
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setNumProjects(users.get(i).getNumProjects() - 1);
            userRepository.save(users.get(i));
        }
        userHasProjectRepository.deleteByProjectId(projectId);
        projectRepository.deleteById(projectId);
    }

    public Boolean checkIfUserMemberOfProject(Integer userId, Integer projectId) {
        Optional<User_has_Project> relation =
                userHasProjectRepository.findByUserIdAndProjectId(userId, projectId);
        if (!relation.isPresent()) {
            return false;
        } else {
            return true;
        }
    }
}
