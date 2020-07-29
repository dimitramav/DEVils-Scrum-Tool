package devils.scrumtool.controllers;

import devils.scrumtool.entities.Project;
import devils.scrumtool.models.ProjectOverview;
import devils.scrumtool.repositories.ProjectRepository;
import devils.scrumtool.services.ProjectService;
// Java libraries
import java.util.List;
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Project API
@RestController
public class ProjectController {

    @Autowired private ProjectRepository projectRepository;

    @Autowired private ProjectService projectService;

    @GetMapping("/projects")
    public Iterable<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/users/{userId}/numOfProjects")
    public Integer getNumOfUserProjects(
            @PathVariable Integer userId, @RequestParam Boolean isDone) {
        return projectRepository.countByUsers_UserIdAndIsDone(userId, isDone);
    }

    @GetMapping("/users/{userId}/projects")
    public List<Project> getUserProjects(
            @PathVariable Integer userId,
            @RequestParam Boolean isDone,
            @RequestParam Integer recordsPerPage,
            @RequestParam Integer currentPage) {
        return projectService.getLimitedUserProjects(userId, isDone, recordsPerPage, currentPage);
    }

    @PostMapping("/users/{userId}/projects")
    public Project insertProject(@RequestBody Project newProject, @PathVariable Integer userId)
            throws Exception {
        return projectService.createProject(newProject, userId);
    }

    @GetMapping("/users/{userId}/projects/{projectId}")
    public ProjectOverview getProjectOverview(
            @PathVariable Integer projectId, @RequestParam Optional<Integer> sprintId)
            throws Exception {
        return projectService.getProjectSprintOverview(projectId, sprintId);
    }

    @PutMapping("/users/{userId}/projects/{projectId}")
    public Project updateProject(
            @PathVariable Integer projectId, @RequestBody Project updatedProject) {
        updatedProject.setId(projectId);
        return projectRepository.save(updatedProject);
    }

    @PutMapping("/users/{userId}/projects/{projectId}/changeIsDone")
    public void archiveActivateProject(
            @PathVariable Integer projectId, @RequestBody Boolean isDoneToBecome) {
        projectRepository.archiveOrActivateProjectById(isDoneToBecome, projectId);
    }

    @DeleteMapping("/users/{userId}/projects/{projectId}")
    public void deleteProject(@PathVariable Integer projectId) {
        projectService.deleteProjectAndRelations(projectId);
    }

    @GetMapping("/users/{userId}/projects/{projectId}/projectAuthorization")
    public Boolean projectAuthorization(
            @PathVariable Integer userId, @PathVariable Integer projectId) {
        return projectService.checkIfUserMemberOfProject(userId, projectId);
    }
}
