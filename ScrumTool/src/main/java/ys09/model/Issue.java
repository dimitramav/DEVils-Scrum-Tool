package ys09.model;


public class Issue {

    private int idIssue;
    private String description;
    private int state;
    private int Task_id;

    // Getters
    public int getId() {
        return idIssue;
    }
    public String getDescription() {
        return description;
    }
    public int getState() {
        return state;
    }
    public int getTask_id() {
        return Task_id;
    }

    // Setters
    public void setId(int idIssue) {
        this.idIssue = idIssue;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setState(int state) {
        this.state = state;
    }
    public void setTask_id(int Task_id) {
        this.Task_id = Task_id;
    }
}