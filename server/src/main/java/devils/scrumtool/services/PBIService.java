package devils.scrumtool.services;

import devils.scrumtool.entities.PBI;
import devils.scrumtool.models.SprintStory;
import devils.scrumtool.repositories.PBIRepository;
// Java libraries
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PBIService {

    @Autowired private PBIRepository pbiRepository;

    @Autowired private ProjectService projectService;

    @Autowired private SprintService sprintService;

    // Methods
    public PBI getPBIById(Integer pbiId) throws Exception {
        Optional<PBI> dbPBI = pbiRepository.findById(pbiId);
        if (!dbPBI.isPresent()) {
            throw new Exception("PBI with id: " + pbiId + " not found!");
        }
        return dbPBI.get();
    }

    public List<PBI> getEpicsOrStories(
            Integer projectId, Boolean isEpic, Optional<Integer> epicId) {
        // Return epic (isEpic = true & epicId = null) or story (isEpic = false)
        if (isEpic == true) {
            return pbiRepository.findByProject_IdAndIsEpic(projectId, isEpic);
        } else {
            return pbiRepository.findByProject_IdAndEpicId(projectId, epicId.get());
        }
    }

    public PBI createPBI(PBI newPBI, Integer projectId) throws Exception {
        // First get the project of this pbi
        newPBI.setProject(projectService.getProjectById(projectId));
        // If this is a story, find its epic
        if (newPBI.getIsEpic() == false) {
            newPBI.setEpic(this.getPBIById(newPBI.getEpic().getId()));
        } else { // If this is an epic, then no epic_id as foreign key
            newPBI.setEpic(null);
        } // Now save the PBI
        return pbiRepository.save(newPBI);
    }

    public PBI editPBI(PBI editedPBI) throws Exception {
        // Find the pbi to be updated
        PBI pbiToUpdate = this.getPBIById(editedPBI.getId());
        // Update the fields
        pbiToUpdate.setTitle(editedPBI.getTitle());
        pbiToUpdate.setDescription(editedPBI.getDescription());
        pbiToUpdate.setPriority(editedPBI.getPriority());
        if (editedPBI.getIsEpic() == false) {
            pbiToUpdate.setEpic(this.getPBIById(editedPBI.getEpic().getId()));
        } // Now save the PBI
        return pbiRepository.save(pbiToUpdate);
    }

    // Get the pbis by sprintId and create for each one a SprintStories java class
    public List<SprintStory> getPBIsBySprintIdAsSprintStories(Integer sprintId) {
        // Firstly get all the pbis with this sprintId
        List<PBI> dbPbis = pbiRepository.findBySprint_Id(sprintId);
        // Then for each pbi create a equivalent sprintStory item
        List<SprintStory> storiesWithSprintId = new ArrayList<SprintStory>();
        for (int i = 0; i < dbPbis.size(); i++) {
            storiesWithSprintId.add(new SprintStory(dbPbis.get(i)));
        }
        return storiesWithSprintId;
    }

    // On new Sprint or on delete, set story's sprintId as the new sprintId or null
    public void editPBIsOnSprintUpdate(List<SprintStory> sprintPbis) throws Exception {
        // Update all selected stories' sprint_ids
        for (int i = 0; i < sprintPbis.size(); i++) {
            // Firstly find the current pbi in database
            PBI editedPBI = this.getPBIById(sprintPbis.get(i).getIdPBI());
            // Set the new sprint_id on current story (or epic)
            if (sprintPbis.get(i).getSprintId() == -1) {
                editedPBI.setSprint(null); // On delete
            } else {
                int sprintId = sprintPbis.get(i).getSprintId();
                editedPBI.setSprint(sprintService.getSprintById(sprintId));
            }
            pbiRepository.save(editedPBI);
        }
    }
}
