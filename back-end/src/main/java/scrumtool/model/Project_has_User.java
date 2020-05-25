package scrumtool.model;


public class Project_has_User {

    private int Project_id;
    private int User_id;
    private String role;

    public Project_has_User(int Project_id, int User_id, String role) {
        this.Project_id = Project_id;
        this.User_id = User_id;
        this.role = role;
    }

    // Getters
    public int getId() {
        return Project_id;
    }
    public int getUser_id() {
        return User_id;
    }
    public String getRole() {
        return role;
    }

    // Setters
    public void setId(int Project_id) {
        this.Project_id = Project_id;
    }
    public void setUser_id(int User_id) {
        this.User_id = User_id;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
