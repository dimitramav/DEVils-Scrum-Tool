package devils.scrumtool.services;

import devils.scrumtool.entities.Sprint;
import devils.scrumtool.models.SprintStory;
import devils.scrumtool.repositories.SprintRepository;
// Java libraries
import java.util.List;
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SprintService {

    @Autowired private SprintRepository sprintRepository;

    @Autowired private PBIService pbiService;

    @Autowired private ProjectService projectService;

    // Methods
    public Sprint getSprintById(Integer sprintId) throws Exception {
        Optional<Sprint> dbSprint = sprintRepository.findById(sprintId);
        if (!dbSprint.isPresent()) {
            throw new Exception("Sprint with id: " + sprintId + " not found!");
        }
        return dbSprint.get();
    }

    @Transactional
    public Sprint createSprint(Sprint newSprint, Integer projectId) throws Exception {
        // First get the project object of this sprint
        newSprint.setProject(projectService.getProjectById(projectId));
        // Check how many sprints are attached to this project and set the new numSprint
        int maxNumSprint = sprintRepository.countByProjectId(projectId);
        if (maxNumSprint > 0) {
            maxNumSprint = sprintRepository.getMaxNumSprintByProjectId(projectId);
        }
        newSprint.setNumSprint(maxNumSprint + 1);
        // Update the previous (last) current sprint to non-current
        sprintRepository.setLastCurrentSprintToNonCurrentByProjectId(projectId);
        newSprint.setIsCurrent(true);
        // Now save the PBI
        return sprintRepository.save(newSprint);
    }

    public Sprint getProjectCurrentSprint(Integer projectId) {
        Optional<Sprint> dbSprint = sprintRepository.findByProject_IdAndIsCurrent(projectId, true);
        if (!dbSprint.isPresent()) {
            return new Sprint();
        }
        return dbSprint.get();
    }

    public Sprint getSprintOfStory(Integer pbiId) throws Exception {
        // Find the story
        int sprintId = pbiService.getPBIById(pbiId).getSprint().getId();
        return this.getSprintById(sprintId);
    }

    @Transactional
    public Sprint updateSprintById(Sprint editedSprint, Integer sprintId) throws Exception {
        // Find this sprint and update only the proper parts
        Sprint updatedSprint = this.getSprintById(sprintId);
        updatedSprint.setGoal(editedSprint.getGoal());
        updatedSprint.setPlan(editedSprint.getPlan());
        updatedSprint.setDeadlineDate(editedSprint.getDeadlineDate());
        // Now save the Sprint
        return sprintRepository.save(updatedSprint);
    }

    @Transactional
    public Sprint updateSprintByIdToCurrent(
            Sprint editedSprint, Integer projectId, Integer sprintId) throws Exception {
        // Find the sprint that is current and convert it to non-current
        Sprint oldCurrentSprint = this.getProjectCurrentSprint(projectId);
        oldCurrentSprint.setIsCurrent(false);
        sprintRepository.save(oldCurrentSprint);
        // Make this sprint to current
        Sprint updatedSprint = this.getSprintById(sprintId);
        updatedSprint.setIsCurrent(editedSprint.getIsCurrent());
        return sprintRepository.save(updatedSprint);
    }

    @Transactional
    public void deleteSprintAndUpdateRelations(Integer projectId, Integer sprintId)
            throws Exception {
        // Find the stories attached to this sprint and set sprintId to null
        List<SprintStory> sprintStories = pbiService.getPBIsBySprintIdAsSprintStories(sprintId);
        for (int i = 0; i < sprintStories.size(); i++) {
            sprintStories.get(i).setSprintId(-1);
        } // When sprintId == -1, it is set as null in database
        pbiService.editPBIsOnSprintUpdate(sprintStories);
        // Delete this sprint
        sprintRepository.deleteById(sprintId);
        // Now check how many sprints exist in this project
        int numOfSprintsInProject = sprintRepository.countByProjectId(projectId);
        // If there are any sprints left, set current sprint the one with max numSprint
        if (numOfSprintsInProject > 0) {
            int maxNumSprint = sprintRepository.getMaxNumSprintByProjectId(projectId);
            sprintRepository.setMaxNumSprintToCurrentByProjectId(projectId, maxNumSprint);
        }
    }
}
