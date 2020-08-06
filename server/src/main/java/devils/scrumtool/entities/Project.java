package devils.scrumtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Notification> notifications;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Sprint> sprints;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<PBI> pbis;

    // Default Constructor
    public Project() {}

    public Project(String title, boolean isDone, Date deadlineDate) {
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

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public Set<PBI> getPbis() {
        return pbis;
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

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setSprints(Set<Sprint> sprints) {
        this.sprints = sprints;
    }

    public void setPbis(Set<PBI> pbis) {
        this.pbis = pbis;
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
