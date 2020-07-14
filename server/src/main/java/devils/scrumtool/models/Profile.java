package devils.scrumtool.models;

import devils.scrumtool.entities.User;
import java.io.Serializable;

// User's profile (no id, password, isAdmin)
public class Profile implements Serializable {

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String country;
    private String job;
    private String company;
    private String description;
    private String bio;
    private String photo;
    private int numProjects;

    // Default Constructor
    public Profile() {}

    public Profile(
            String username,
            String email,
            String firstname,
            String lastname,
            String country,
            String job,
            String company,
            String description,
            String bio,
            String photo,
            int numProjects) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.job = job;
        this.company = company;
        this.description = description;
        this.bio = bio;
        this.photo = photo;
        this.numProjects = numProjects;
    }

    // Constructor with User element
    public Profile(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.country = user.getCountry();
        this.job = user.getJob();
        this.company = user.getCompany();
        this.description = user.getDescription();
        this.bio = user.getBio();
        this.photo = user.getPhoto();
        this.numProjects = user.getNumProjects();
    }

    // Getters
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

    public int getNumProjects() {
        return numProjects;
    }

    // Setters
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

    public void setNumProjects(int numProjects) {
        this.numProjects = numProjects;
    }

    @Override
    public String toString() {
        return "Profile [username="
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
                + ", num_projects="
                + numProjects
                + "]";
    }
}
