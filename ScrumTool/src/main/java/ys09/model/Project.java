package ys09.model;

public class Project {

    private final long idProject;
    private final String title;
    private final int isDone;
    private final String deadlineDate;

    public Project(long idProject, String title, int isDone, String deadlineDate) {
        this.idProject = idProject;
        this.title = title;
        this.isDone = isDone;
        this.deadlineDate = deadlineDate;
    }

    public long getId() {
        return idProject;
    }

    public String getTitle() {
        return title;
    }

    public int getIsDone() {
        return isDone;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }
}
