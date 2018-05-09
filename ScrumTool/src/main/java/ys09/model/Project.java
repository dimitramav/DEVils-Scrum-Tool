package ys09.model;

import java.util.Date;


public class Project {

    private int idProject;
    private String title;
    private boolean isDone;
    private Date deadlineDate;

    public Project(int idProject, String title, boolean isDone, Date deadlineDate) {
        this.idProject = idProject;
        this.title = title;
        this.isDone = isDone;
        this.deadlineDate = deadlineDate;
    }

    // Getters
    public int getId() {
        return idProject;
    }
    public String getTitle() {
        return title;
    }
    public boolean getIsDone() {
        return isDone;
    }
    public Date getDeadlineDate() {
        return deadlineDate;
    }

    // Setters
    public void setId(int idProject) {
        this.idProject = idProject;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }
    public void setDeadlineDate(Date deadlineDatee) {
        this.deadlineDate = deadlineDate;
    }
}
