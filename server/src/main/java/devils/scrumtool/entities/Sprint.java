package devils.scrumtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sprints")
public class Sprint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private int id;

    @Column(name = "deadline_date", nullable = false)
    private Date deadlineDate;

    @Column(name = "goal")
    private String goal;

    @Column(name = "plan")
    private String plan;

    @Column(name = "is_current", nullable = false)
    private boolean isCurrent;

    @Column(name = "num_sprint")
    private int numSprint;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @JsonIgnore
    @OneToMany(mappedBy = "sprint")
    private Set<PBI> pbis;

    // Default Constructor
    public Sprint() {}

    public Sprint(Date deadlineDate, String goal, String plan, boolean isCurrent) {
        this.deadlineDate = deadlineDate;
        this.goal = goal;
        this.plan = plan;
        this.isCurrent = isCurrent;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public String getGoal() {
        return goal;
    }

    public String getPlan() {
        return plan;
    }

    public boolean getIsCurrent() {
        return isCurrent;
    }

    public int getNumSprint() {
        return numSprint;
    }

    public Project getProject() {
        return project;
    }

    public Set<PBI> getPbis() {
        return pbis;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public void setNumSprint(int numSprint) {
        this.numSprint = numSprint;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setPbis(Set<PBI> pbis) {
        this.pbis = pbis;
    }

    @Override
    public String toString() {
        return "Sprint [id="
                + id
                + ", deadline_date="
                + deadlineDate
                + ", goal="
                + goal
                + ", plan="
                + plan
                + ", is_current="
                + isCurrent
                + ", num_sprint="
                + numSprint
                + ", project_id="
                + project.getId()
                + "]";
    }
}
