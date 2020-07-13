package devils.scrumtool.services;

import devils.scrumtool.entities.Project;
import devils.scrumtool.entities.User;
import devils.scrumtool.entities.User_has_Project;
import devils.scrumtool.entities.User_has_Project.UserHasProjectId;
import devils.scrumtool.repositories.ProjectRepository;
import devils.scrumtool.repositories.UserRepository;
import devils.scrumtool.repositories.User_has_ProjectRepository;
// Java libraries
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
}
