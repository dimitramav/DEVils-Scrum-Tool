package ys09.model;

public class PBI {

    private int idPBI;
    private String title;
    private String description;
    private int order;
    private Boolean isEpic;
    private int Project_id;
    private int Epic_id;
    private int Sprint_id;

    public PBI(int idPBI, String title, String description, int order, Boolean isEpic, int project_id, int epic_id, int sprint_id) {
        this.idPBI = idPBI;
        this.title = title;
        this.description = description;
        this.order = order;
        this.isEpic = isEpic;
        Project_id = project_id;
        Epic_id = epic_id;
        Sprint_id = sprint_id;
    }

    public int getEpic_id() {
        return Epic_id;
    }

    public void setEpic_id(int epic_id) {
        this.Epic_id = epic_id;
    }

    public int getSprint_id() {
        return Sprint_id;
    }

    public void setSprint_id(int sprint_id) {
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
