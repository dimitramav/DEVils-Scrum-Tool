package devils.scrumtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "user_has_project")
public class User_has_Project implements Serializable {

    @EmbeddedId private UserHasProjectId id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("project_id")
    private Project project;

    @Column(name = "role", nullable = false)
    private String role;

    // Default Constructor
    public User_has_Project() {}

    public User_has_Project(UserHasProjectId id, String role) {
        this.id = id;
        this.role = role;
    }

    // Getters
    public UserHasProjectId getId() {
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
    public void setId(UserHasProjectId id) {
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
        return "User_has_Project [user_id="
                + id.getUserId()
                + ", project_id="
                + id.getProjectId()
                + ", role="
                + role
                + "]";
    }

    // Embedded class for id
    @Embeddable
    public static class UserHasProjectId implements Serializable {

        @Column(name = "user_id")
        private int userId;

        @Column(name = "project_id")
        private int projectId;

        // Default Constructor
        public UserHasProjectId() {}

        public UserHasProjectId(int userId, int projectId) {
            this.userId = userId;
            this.projectId = projectId;
        }

        // Getters
        public int getUserId() {
            return userId;
        }

        public int getProjectId() {
            return projectId;
        }

        // Setters
        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setProjectId(int projectId) {
            this.projectId = projectId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserHasProjectId that = (UserHasProjectId) o;
            return Objects.equals(userId, that.userId) && Objects.equals(projectId, that.projectId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, projectId);
        }
    }
}
