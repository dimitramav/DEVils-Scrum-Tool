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

    // Constructor for Sign Up
    public User(int idUser, String username,String mail, String firstname, String lastname, String password) {
      this.idUser = idUser;
      this.username = username;
      this.mail = mail;
      this.firstname = firstname;
      this.lastname = lastname;
      this.password = password;
    }

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

    public void setPassword(String password) {
      this.password = password;
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

}
