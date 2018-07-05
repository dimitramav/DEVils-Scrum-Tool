package ys09.model;


public class Notification {

    private int idNotification;
    private int Project_id;
    private String projectTitle;
    private String role;
    private String FromUsername;
    private String ToUserEmail;
    private String type;
    private String text;

    // Getters
    public int getIdNotification() {
        return idNotification;
    }
    public int getProject_id() {
        return Project_id;
    }
    public String getProjectTitle() {
        return projectTitle;
    }
    public String getRole() {
        return role;
    }
    public String getFromUsername() {
        return FromUsername;
    }
    public String getToUserEmail() {
        return ToUserEmail;
    }
    public String getType() {
        return type;
    }
    public String getText() {
        return text;
    }

    // Setters
    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }
    public void setProject_id(int Project_id) {
        this.Project_id = Project_id;
    }
    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setFromUsername(String FromUsername) {
        this.FromUsername = FromUsername;
    }
    public void setToUserEmail(String ToUserEmail) {
        this.ToUserEmail = ToUserEmail;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setText(String text) {
        this.text = text;
    }
}