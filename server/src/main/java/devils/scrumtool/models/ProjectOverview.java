package devils.scrumtool.models;

import devils.scrumtool.entities.Project;
import devils.scrumtool.entities.Sprint;
import java.io.Serializable;
import java.sql.Date;

// Project's current sprint overview
public class ProjectOverview implements Serializable {

    private Project project;

    private int currentSprintId;
    private int currentSprintNum;
    private Date currentSprintExpDate;
    private String currentSprintGoal;
    private int todo;
    private int doing;
    private int done;
    private int issues;

    // Default Constructor
    public ProjectOverview() {}

    public ProjectOverview(
            Project project, Sprint sprint, int todo, int doing, int done, int issues) {
        this.project = project;
        this.currentSprintId = sprint.getId();
        this.currentSprintNum = sprint.getNumSprint();
        this.currentSprintExpDate = sprint.getDeadlineDate();
        this.currentSprintGoal = sprint.getGoal();
        this.todo = todo;
        this.doing = doing;
        this.done = done;
        this.issues = issues;
    }

    // Getters
    public Project getProject() {
        return project;
    }

    public int getCurrentSprintId() {
        return currentSprintId;
    }

    public int getCurrentSprintNum() {
        return currentSprintNum;
    }

    public Date getCurrentSprintExpDate() {
        return currentSprintExpDate;
    }

    public String getCurrentSprintGoal() {
        return currentSprintGoal;
    }

    public int getTodo() {
        return todo;
    }

    public int getDoing() {
        return doing;
    }

    public int getDone() {
        return done;
    }

    public int getIssues() {
        return issues;
    }

    // Setters
    public void setProject(Project project) {
        this.project = project;
    }

    public void setCurrentSprintId(int currentSprintId) {
        this.currentSprintId = currentSprintId;
    }

    public void setCurrentSprintNum(int currentSprintNum) {
        this.currentSprintNum = currentSprintNum;
    }

    public void setCurrentSprintExpDate(Date currentSprintExpDate) {
        this.currentSprintExpDate = currentSprintExpDate;
    }

    public void setCurrentSprintGoal(String currentSprintGoal) {
        this.currentSprintGoal = currentSprintGoal;
    }

    public void setTodo(int todo) {
        this.todo = todo;
    }

    public void setDoing(int doing) {
        this.doing = doing;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public void setIssues(int issues) {
        this.issues = issues;
    }

    public void addIssues(int addedIssues) {
        this.issues += addedIssues;
    }

    @Override
    public String toString() {
        return "ProjectOverview ["
                + project.toString()
                + ", currentSprintId="
                + currentSprintId
                + ", currentSprintNum="
                + currentSprintNum
                + ", currentSprintExpDate="
                + currentSprintExpDate
                + ", currentSprintGoal="
                + currentSprintGoal
                + ", todo="
                + todo
                + ", doing="
                + doing
                + ", done="
                + done
                + ", issues="
                + issues
                + "]";
    }
}
