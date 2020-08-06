package devils.scrumtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import devils.scrumtool.models.Profile;
// Java libraries
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "country")
    private String country;

    @Column(name = "job")
    private String job;

    @Column(name = "company")
    private String company;

    @Column(name = "description")
    private String description;

    @Column(name = "bio")
    private String bio;

    @Column(name = "photo")
    private String photo;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    @Column(name = "num_projects", nullable = false)
    private int numProjects;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<User_has_Project> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Notification> senderNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private Set<Notification> receiverNotifications;

    // Default Constructor
    public User() {}

    // Constructor for Sign Up
    public User(String username, String email, String firstname, String lastname, String password) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.isAdmin = false;
        this.numProjects = 0;
    }

    // Insert profile changes to a user
    public void updateUserProfile(Profile profile) {
        this.username = profile.getUsername();
        this.email = profile.getEmail();
        this.firstname = profile.getFirstname();
        this.lastname = profile.getLastname();
        this.country = profile.getCountry();
        this.job = profile.getJob();
        this.company = profile.getCompany();
        this.description = profile.getDescription();
        this.bio = profile.getBio();
        this.photo = profile.getPhoto();
    }

    // Getters
    public int getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public String getCountry() {
        return country;
    }

    public String getJob() {
        return job;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    public String getBio() {
        return bio;
    }

    public String getPhoto() {
        return photo;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public int getNumProjects() {
        return numProjects;
    }

    // By returning relational objects, we get an infinite loop
    // So use @JsonIgnore annotations to avoid them
    public Set<User_has_Project> getProjects() {
        return projects;
    }

    public Set<Notification> getSenderNotifications() {
        return senderNotifications;
    }

    public Set<Notification> getReceiverNotifications() {
        return receiverNotifications;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setNumProjects(int numProjects) {
        this.numProjects = numProjects;
    }

    public void setProjects(Set<User_has_Project> projects) {
        this.projects = projects;
    }

    public void setSenderNotifications(Set<Notification> senderNotifications) {
        this.senderNotifications = senderNotifications;
    }

    public void setReceiverNotifications(Set<Notification> receiverNotifications) {
        this.receiverNotifications = receiverNotifications;
    }

    @Override
    public String toString() {
        return "User [id="
                + id
                + ", username="
                + username
                + ", email="
                + email
                + ", firstname="
                + firstname
                + ", lastname="
                + lastname
                + ", password="
                + password
                + ", country="
                + country
                + ", job="
                + job
                + ", company="
                + company
                + ", description="
                + description
                + ", bio="
                + bio
                + ", photo="
                + photo
                + ", is_admin="
                + isAdmin
                + ", num_projects="
                + numProjects
                + "]";
    }
}
