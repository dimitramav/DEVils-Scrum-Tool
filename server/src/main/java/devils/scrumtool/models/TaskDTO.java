package devils.scrumtool.models;

import devils.scrumtool.entities.Task;
import java.io.Serializable;

// Task Data Transfer Object for Rest communication
public class TaskDTO implements Serializable {

    private int id;
    private String description;
    private Integer state;
    private Integer storyId;

    // Default Constructor
    public TaskDTO() {}

    public TaskDTO(int id, String description, Integer state, Integer storyId) {
        this.id = id;
        this.description = description;
        this.state = state;
        this.storyId = storyId;
    }

    // Constructor with PBI element
    public TaskDTO(Task task) {
        this.id = task.getId();
        this.description = task.getDescription();
        this.state = task.getState();
        this.storyId = task.getStory().getId();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Integer getState() {
        return state;
    }

    public Integer getStoryId() {
        return storyId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    @Override
    public String toString() {
        return "TaskDTO [id="
                + id
                + ", description="
                + description
                + ", state="
                + state
                + ", storyId="
                + storyId
                + "]";
    }
}
