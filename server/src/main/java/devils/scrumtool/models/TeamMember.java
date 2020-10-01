package devils.scrumtool.models;

import devils.scrumtool.entities.User;
import java.io.Serializable;

// Member of a project team
public class TeamMember implements Serializable {

    private int idUser;
    private int idProject;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String photo;
    private String role;

    // Default Constructor
    public TeamMember() {}

    public TeamMember(
            int idUser,
            int idProject,
            String username,
            String email,
            String firstname,
            String lastname,
            String photo,
            String role) {
        this.idUser = idUser;
        this.idProject = idProject;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.photo = photo;
        this.role = role;
    }

    // Constructor with User element
    public TeamMember(User user, int projectId, String role) {
        this.idUser = user.getId();
        this.idProject = projectId;
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.photo = user.getPhoto();
        this.role = role;
    }

    // Getters
    public int getIdUser() {
        return idUser;
    }

    public int getIdProject() {
        return idProject;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhoto() {
        return photo;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "TeamMember [idUser="
                + idUser
                + ", idProject="
                + idProject
                + ", username="
                + username
                + ", email="
                + email
                + ", firstname="
                + firstname
                + ", lastname="
                + lastname
                + ", photo="
                + photo
                + ", role="
                + role
                + "]";
    }
}
