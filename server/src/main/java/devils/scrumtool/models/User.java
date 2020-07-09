package devils.scrumtool.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
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
    private boolean is_admin;

    @Column(name = "num_projects", nullable = false)
    private int num_projects;

    // Default Constructor
    public User() {}

    // Constructor for Sign Up
    public User(
            int id,
            String username,
            String email,
            String firstname,
            String lastname,
            String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
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

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
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
        return is_admin;
    }

    public int getNumProjects() {
        return num_projects;
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

    public void setIsAdmin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public void setNumProjects(int num_projects) {
        this.num_projects = num_projects;
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
                + is_admin
                + ", num_projects="
                + num_projects
                + "]";
    }
}
