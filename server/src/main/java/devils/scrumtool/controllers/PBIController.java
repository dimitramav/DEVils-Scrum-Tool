package devils.scrumtool.controllers;

import devils.scrumtool.entities.PBI;
import devils.scrumtool.models.SprintStory;
import devils.scrumtool.repositories.PBIRepository;
import devils.scrumtool.services.PBIService;
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

// PBI API
@RestController
public class PBIController {

    @Autowired private PBIRepository pbiRepository;

    @Autowired private PBIService pbiService;

    @GetMapping("/pbis")
    public Iterable<PBI> getAllPBIs() {
        return pbiRepository.findAll();
    }

    @GetMapping("/users/{userId}/projects/{projectId}/pbis")
    public List<PBI> getProjectPBIs(
            @PathVariable Integer projectId,
            @RequestParam Boolean isEpic,
            @RequestParam Optional<Integer> epicId) {
        return pbiService.getEpicsOrStories(projectId, isEpic, epicId);
    }

    @PostMapping("/users/{userId}/projects/{projectId}/pbis")
    public PBI insertPBI(@RequestBody PBI newPBI, @PathVariable Integer projectId)
            throws Exception {
        return pbiService.createPBI(newPBI, projectId);
    }

    @PutMapping("/users/{userId}/projects/{projectId}/pbis")
    public PBI updatePBI(@RequestBody PBI editedPBI) throws Exception {
        return pbiService.editPBI(editedPBI);
    }

    @PutMapping("/users/{userId}/projects/{projectId}/pbis/sprintUpdate")
    public void sprintUpdatePBI(@RequestBody List<SprintStory> sprintPbis) throws Exception {
        pbiService.editPBIsOnSprintUpdate(sprintPbis);
    }

    @DeleteMapping("/users/{userId}/projects/{projectId}/pbis/{pbiId}")
    public void deletePBI(@PathVariable Integer pbiId) {
        pbiRepository.deleteById(pbiId);
    }

    @GetMapping("/users/{userId}/projects/{projectId}/sprintStories")
    public List<PBI> getProjectSprintStories(@RequestParam Integer sprintId) {
        return pbiRepository.findBySprint_Id(sprintId);
    }
}
