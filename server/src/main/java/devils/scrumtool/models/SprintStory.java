package devils.scrumtool.models;

import devils.scrumtool.entities.PBI;
import java.io.Serializable;

// Sprint story's keys for updates
public class SprintStory implements Serializable {

    private int idPBI;
    private Integer sprintId;
    private Integer projectId;
    private Integer epicId;

    // Default Constructor
    public SprintStory() {}

    public SprintStory(int idPBI, Integer sprintId, Integer projectId, Integer epicId) {
        this.idPBI = idPBI;
        this.sprintId = sprintId;
        this.projectId = projectId;
        this.epicId = epicId;
    }

    // Constructor with PBI element
    public SprintStory(PBI story) {
        this.idPBI = story.getId();
        this.sprintId = story.getSprint().getId();
        this.projectId = story.getProject().getId();
        this.epicId = story.getEpic().getId();
    }

    // Getters
    public int getIdPBI() {
        return idPBI;
    }

    public Integer getSprintId() {
        return sprintId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    // Setters
    public void setIdPBI(int idPBI) {
        this.idPBI = idPBI;
    }

    public void setSprintId(Integer sprintId) {
        this.sprintId = sprintId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "SprintStory [idPBI="
                + idPBI
                + ", projectId="
                + projectId
                + ", sprintId="
                + sprintId
                + ", epicId="
                + epicId
                + "]";
    }
}
