package scrumtool.model;

import java.util.Date;


public class Sprint {

    private int idSprint;
    private Date deadlineDate;
    private String goal;
    private String plan;
    private Boolean isCurrent;
    private int numSprint;
    private int Project_id;

    public int getIdSprint() {
        return idSprint;
    }
    public void setIdSprint(int idSprint) {
        this.idSprint = idSprint;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }
    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getGoal() {
        return goal;
    }
    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getPlan() {
        return plan;
    }
    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Boolean getCurrent() {
        return isCurrent;
    }
    public void setCurrent(Boolean current) {
        this.isCurrent = current;
    }

    public int getNumSprint() {
        return numSprint;
    }
    public void setNumSprint(int numSprint) {
        this.numSprint = numSprint;
    }

    public int getProject_id() {
        return Project_id;
    }
    public void setProject_id(int project_id) {
        this.Project_id = project_id;
    }
}
