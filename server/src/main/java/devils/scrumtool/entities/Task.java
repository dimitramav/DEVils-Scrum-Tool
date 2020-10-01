package devils.scrumtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private int id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "state", nullable = false)
    private int state;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pbi_id", referencedColumnName = "id")
    private PBI story;

    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<Issue> issues;

    // Default Constructor
    public Task() {}

    public Task(String description, int state) {
        this.description = description;
        this.state = state;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getState() {
        return state;
    }

    public PBI getStory() {
        return story;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setStory(PBI story) {
        this.story = story;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return "Task [id="
                + id
                + ", description="
                + description
                + ", state="
                + state
                + ", pbi_id="
                + story.getId()
                + "]";
    }
}
