package devils.scrumtool.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_has_project")
public class User_has_Project {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "role", nullable = false)
    private String role;

    // Defa Constructor
    public User_has_Project() {}

    public User_has_Project(int id, User user, Project project, String role) {
        this.id = id;
        this.user = user;
        this.project = project;
        this.role = role;
    }

    // Getters
    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User_has_Project [id="
                + id
                + ", user_id="
                + user.getId()
                + ", project_id="
                + project.getId()
                + ", role="
                + role
                + "]";
    }
}
