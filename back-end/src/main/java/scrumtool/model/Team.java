package scrumtool.model;


public class Team {

    private int idUser;
    private String mail;
    private String firstname;
    private String lastname;
    private String photo;
    private String role;

    // Getters
    public Integer getIdUser() {
        return idUser;
    }
    public String getMail() {
        return mail;
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
    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public void setRole(String role) {
        this.role = role;
    }

}
