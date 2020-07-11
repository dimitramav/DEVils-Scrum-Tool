package devils.scrumtool.entities;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "is_done", nullable = false)
    private boolean isDone;

    @Column(name = "deadline_date")
    private Date deadlineDate;

    @OneToMany(mappedBy = "project")
    Set<User_has_Project> relations;

    // Default Constructor
    public Project() {}

    public Project(int id, String title, boolean isDone, Date deadlineDate) {
        this.id = id;
        this.title = title;
        this.isDone = isDone;
        this.deadlineDate = deadlineDate;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public Set<User_has_Project> getRelations() {
        return relations;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public void setRelations(Set<User_has_Project> relations) {
        this.relations = relations;
    }

    @Override
    public String toString() {
        return "Project [id="
                + id
                + ", title="
                + title
                + ", is_done="
                + isDone
                + ", deadline_date="
                + deadlineDate
                + "]";
    }
}
