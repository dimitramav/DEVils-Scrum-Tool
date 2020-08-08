package devils.scrumtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private int id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    // @MapsId("project_id")
    private Project project;

    @Column(name = "project_title")
    private String projectTitle;

    @Column(name = "role")
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_username", referencedColumnName = "username")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_email", referencedColumnName = "email")
    private User receiver;

    @Column(name = "type")
    private String type;

    @Column(name = "message")
    private String message;

    // Default Constructor
    public Notification() {}

    public Notification(String projectTitle, String role, String type, String message) {
        this.projectTitle = projectTitle;
        this.role = role;
        this.type = type;
        this.message = message;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getRole() {
        return role;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Project [id="
                + id
                + ", project_id="
                + project.getId()
                + ", project_title="
                + projectTitle
                + ", role="
                + role
                + ", from_username="
                + sender.getUsername()
                + ", to_user_email="
                + receiver.getEmail()
                + ", type="
                + type
                + ", message="
                + message
                + "]";
    }
}
