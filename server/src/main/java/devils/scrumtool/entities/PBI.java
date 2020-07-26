package devils.scrumtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pbis")
public class PBI implements Serializable {

    @Id
    @GeneratedValue
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id", referencedColumnName = "id")
    private Sprint sprint;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "epic_id", referencedColumnName = "id")
    private PBI epic;

    @JsonIgnore
    @OneToMany(mappedBy = "epic", cascade = CascadeType.ALL)
    private Set<PBI> stories;

    // Default Constructor
    public PBI() {}

    public PBI(int id, String title, String description, int priority, boolean isEpic) {
        this.id = id;
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
