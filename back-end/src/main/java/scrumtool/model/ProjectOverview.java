package scrumtool.model;

import java.util.Date;


public class ProjectOverview {

    public  Project project;

    private int currentSprintId;
    private int currentSprintNum;
    private Date currentSprintExpDate;
    private int todo;
    private int doing;
    private int done;
    private int todoIssues;
    private int doingIssues;
    private int doneIssues;


    // Getters
    public int getCurrentSprintId() {
        return currentSprintId;
    }
    public int getCurrentSprintNum() {
        return currentSprintNum;
    }
    public Date getCurrentSprintExpDate() {
        return currentSprintExpDate;
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
    public int getTodoIssues() {
        return todoIssues;
    }
    public int getDoingIssues() {
        return doingIssues;
    }
    public int getDoneIssues() {
        return doneIssues;
    }

    // Setters
    public void setCurrentSprintId(int currentSprintId) {
        this.currentSprintId = currentSprintId;
    }
    public void setCurrentSprintNum(int currentSprintNum) {
        this.currentSprintNum = currentSprintNum;
    }
    public void setCurrentSprintExpDate(Date currentSprintExpDate) {
        this.currentSprintExpDate = currentSprintExpDate;
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
    public void setTodoIssues(int todoIssues) {
        this.todoIssues = todoIssues;
    }
    public void setDoingIssues(int doingIssues) {
        this.doingIssues = doingIssues;
    }
    public void setDoneIssues(int doneIssues) {
        this.doneIssues = doneIssues;
    }
}
