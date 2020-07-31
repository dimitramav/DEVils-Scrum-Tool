package devils.scrumtool.controllers;

import devils.scrumtool.entities.Sprint;
import devils.scrumtool.repositories.SprintRepository;
import devils.scrumtool.services.SprintService;
// Java libraries
import java.util.List;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Sprint API
@RestController
public class SprintController {

    @Autowired private SprintRepository sprintRepository;

    @Autowired private SprintService sprintService;

    @GetMapping("/sprints")
    public Iterable<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }

    @GetMapping("/users/{userId}/projects/{projectId}/sprints")
    public List<Sprint> getProjectSprints(@PathVariable Integer projectId) {
        return sprintRepository.findByProject_IdOrderByDeadlineDateDesc(projectId);
    }

    @PostMapping("/users/{userId}/projects/{projectId}/sprints")
    public Sprint insertSprint(@RequestBody Sprint newSprint, @PathVariable Integer projectId)
            throws Exception {
        return sprintService.createSprint(newSprint, projectId);
    }

    @GetMapping("/users/{userId}/projects/{projectId}/sprints/{sprintId}")
    public Sprint getSprint(@PathVariable Integer sprintId) throws Exception {
        return sprintService.getSprintById(sprintId);
    }

    @PutMapping("/users/{userId}/projects/{projectId}/sprints/{sprintId}")
    public Sprint updateSprint(@RequestBody Sprint editedSprint, @PathVariable Integer sprintId)
            throws Exception {
        return sprintService.updateSprintById(editedSprint, sprintId);
    }

    @PutMapping("/users/{userId}/projects/{projectId}/sprints/{sprintId}/makeSprintCurrent")
    public Sprint updateSprintToCurrent(
            @RequestBody Sprint editedSprint,
            @PathVariable Integer projectId,
            @PathVariable Integer sprintId)
            throws Exception {
        return sprintService.updateSprintByIdToCurrent(editedSprint, projectId, sprintId);
    }

    @DeleteMapping("/users/{userId}/projects/{projectId}/sprints/{sprintId}")
    public void deleteSprint(@PathVariable Integer projectId, @PathVariable Integer sprintId)
            throws Exception {
        sprintService.deleteSprintAndUpdateRelations(projectId, sprintId);
    }
}
