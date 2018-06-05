package ys09.model;


public class Task {

    private int idTask;
    private String description;
    private int state;
    private int PBI_id;

    // Getters
    public int getId() {
        return idTask;
    }
    public String getDescription() {
        return description;
    }
    public int getState() {
        return state;
    }
    public int getPBI_id() {
        return PBI_id;
    }

    // Setters
    public void setId(int idTask) {
        this.idTask = idTask;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setState(int state) {
        this.state = state;
    }
    public void setPBI_id(int PBI_id) {
        this.PBI_id = PBI_id;
    }
}