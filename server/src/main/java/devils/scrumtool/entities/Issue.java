package devils.scrumtool.entities;

// import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "issues")
public class Issue implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "serial")
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    // Default Constructor
    public Issue() {}

    public Issue(int id, String description) {
        this.id = id;
        this.description = description;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Task getTask() {
        return task;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Issue [id="
                + id
                + ", description="
                + description
                + ", task_id="
                + task.getId()
                + "]";
    }
}
