package ys09.model;

public class PBI {

    private int idPBI;
    private String title;
    private String description;
    private int priority;
    private Boolean isEpic;
    private int Project_id;
    private Integer Epic_id;
    private Integer Sprint_id;

    public PBI(int idPBI, String title, String description, int priority, Boolean isEpic, int project_id, int epic_id, int sprint_id) {
        this.idPBI = idPBI;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.isEpic = isEpic;
        Project_id = project_id;
        Epic_id = epic_id;
        Sprint_id = sprint_id;
    }

    public Integer getEpic_id() {
        return Epic_id;
    }

    public void setEpic_id(Integer epic_id) {
        this.Epic_id = epic_id;
    }

    public Integer getSprint_id() {
        return Sprint_id;
    }

    public void setSprint_id(Integer sprint_id) {
        this.Sprint_id = sprint_id;
    }

    public int getIdPBI() {
        return idPBI;
    }

    public void setIdPBI(int idPBI) {
        this.idPBI = idPBI;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Boolean getIsEpic() {
        return isEpic;
    }

    public void setIsEpic(Boolean epic) {
        isEpic = epic;
    }

    public int getProject_id() {
        return Project_id;
    }

    public void setProject_id(int project_id) {
        Project_id = project_id;
    }
}
