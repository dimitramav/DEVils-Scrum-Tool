package devils.scrumtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Date;
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
    @Column(name = "id", columnDefinition = "serial")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "is_done", nullable = false)
    private boolean isDone;

    @Column(name = "deadline_date")
    private Date deadlineDate;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<User_has_Project> users;

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

    public Set<User_has_Project> getUsers() {
        return users;
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

    public void setUsers(Set<User_has_Project> users) {
        this.users = users;
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
