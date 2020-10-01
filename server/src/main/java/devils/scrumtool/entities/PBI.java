package devils.scrumtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pbis")
public class PBI implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "priority", nullable = false)
    private int priority;

    @Column(name = "is_epic", nullable = false)
    private boolean isEpic;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sprint_id", referencedColumnName = "id")
    private Sprint sprint;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "epic_id", referencedColumnName = "id")
    private PBI epic;

    @JsonIgnore
    @OneToMany(mappedBy = "epic", cascade = CascadeType.ALL)
    private Set<PBI> stories;

    @JsonIgnore
    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    // Default Constructor
    public PBI() {}

    public PBI(String title, String description, int priority, boolean isEpic) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.isEpic = isEpic;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public boolean getIsEpic() {
        return isEpic;
    }

    public Project getProject() {
        return project;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public PBI getEpic() {
        return epic;
    }

    public Set<PBI> getStories() {
        return stories;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setIsEpic(boolean isEpic) {
        this.isEpic = isEpic;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public void setEpic(PBI epic) {
        this.epic = epic;
    }

    public void setStories(Set<PBI> stories) {
        this.stories = stories;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "PBI [id="
                + id
                + ", title="
                + title
                + ", description="
                + description
                + ", priority="
                + priority
                + ", is_epic="
                + isEpic
                + ", project_id="
                + project.getId()
                + ", sprint_id="
                + sprint.getId()
                + ", epic_id="
                + epic.getId()
                + "]";
    }
}
