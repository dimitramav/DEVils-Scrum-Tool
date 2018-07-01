package ys09.model;

// Plain Java Object for Mapping
public class User {

    // Mandatory
    private int idUser;
    // Mandatory
    private String username;
    // Mandatory
    private String mail;
    // Mandatory
    private String firstname;
    // Mandatory
    private String lastname;
    // Mandatory
    private String password;

    private String country;

    private String job;

    private String company;

    private String description;

    private String bio;

    private String photo;

    private int isAdmin;

    private int numProjects;

    // Default Constructor
    public User() { }

    // Constructor for Sign Up
    public User(int idUser, String username, String mail, String firstname, String lastname, String password) {
      this.idUser = idUser;
      this.username = username;
      this.mail = mail;
      this.firstname = firstname;
      this.lastname = lastname;
      this.password = password;
    }

    // Getters
    public int getId() {
      return idUser;
    }
    public String getUsername() {
      return username;
    }
    public String getEmail() {
      return mail;
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
    public int getIsAdmin() {
      return isAdmin;
    }
    public int getNumOfProjects() {
      return numProjects;
    }

    // Setters
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setMail(String mail) {
        this.mail = mail;
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
    public void setNumProjects(int numProjects) {
        this.numProjects = numProjects;
    }
    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
    public void setNumOfProjects(int numProjects) {
        this.numProjects = numProjects;
    }
}
