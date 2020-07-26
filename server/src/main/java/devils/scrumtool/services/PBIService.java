package devils.scrumtool.services;

import devils.scrumtool.entities.PBI;
import devils.scrumtool.entities.Project;
import devils.scrumtool.repositories.PBIRepository;
import devils.scrumtool.repositories.ProjectRepository;
// Java libraries
import java.util.List;
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PBIService {

    @Autowired private PBIRepository pbiRepository;

    @Autowired private ProjectRepository projectRepository;

    public List<PBI> getEpicsOrStories(
            Integer projectId, Boolean isEpic, Optional<Integer> epicId) {
        // Return epic (isEpic = true & epicId = null) or story (isEpic = false)
        if (isEpic == true) {
            return pbiRepository.findByProject_IdAndIsEpic(projectId, isEpic);
        } else {
            return pbiRepository.findByProject_IdAndEpicId(projectId, epicId.get());
        }
    }

    public PBI createOrEditPBI(PBI newPBI, Integer projectId) throws Exception {
        // First get the project of this pbi
        Optional<Project> dbProject = projectRepository.findById(projectId);
        if (!dbProject.isPresent()) {
            throw new Exception("Project with id: " + projectId + " not found!");
        }
        newPBI.setProject(dbProject.get());
        // If this is a story, find its epic
        if (newPBI.getIsEpic() == false) {
            Optional<PBI> dbEpic = pbiRepository.findById(newPBI.getEpic().getId());
            if (!dbEpic.isPresent()) {
                throw new Exception("Epic with id: " + newPBI.getEpic().getId() + " not found!");
            }
            newPBI.setEpic(dbEpic.get());
        } else { // If this is an epic, then no epic_id as foreign key
            newPBI.setEpic(null);
        } // Now save the PBI
        PBI insertedPBI = pbiRepository.save(newPBI);
        return insertedPBI;
    }
}
