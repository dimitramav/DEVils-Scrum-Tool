package devils.scrumtool.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import devils.scrumtool.entities.Sprint;
// Java libraries
import java.util.List;
import java.util.Optional;
// JUnit & Spring libraries
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class SprintRepositoryTest {

    @Autowired private SprintRepository repository;
    // given
    private int projectId = 2;
    private int sprintId = 1;
    private String sprintPlan = "Test Sprint";
    private int sprintNumSprint = 1;

    @Test
    void countByProjectId() throws Exception {
        // when
        int numOfSprints = this.repository.countByProjectId(this.projectId);
        // then
        assertThat(numOfSprints).isEqualTo(1);
    }

    @Test
    void getMaxNumSprintByProjectId() throws Exception {
        // when
        int maxNumSprint = this.repository.getMaxNumSprintByProjectId(this.projectId);
        // then
        assertThat(maxNumSprint).isEqualTo(1);
    }

    @Test
    void findCurrentSprintByProjectId() throws Exception {
        // when
        Optional<Sprint> currentSprint =
                this.repository.findByProject_IdAndIsCurrent(this.projectId, true);
        // then
        assertThat(currentSprint.isPresent()).isTrue();
        assertThat(currentSprint.get().getPlan()).isEqualTo(this.sprintPlan);
    }

    @Test
    void findByProjectIdOrderByDeadlineDateDescending() throws Exception {
        // when
        List<Sprint> sprintsInProject =
                this.repository.findByProject_IdOrderByDeadlineDateDesc(this.projectId);
        // then
        assertThat(sprintsInProject.size()).isEqualTo(1);
    }

    // Conserning the data given in data.sql, this test cannot be executed
    /*@Test
    void setCurrentSprintToNonCurrentAndThenBackToCurrentAgain() throws Exception {
        // when
        this.repository.setLastCurrentSprintToNonCurrentByProjectId(this.projectId);
        // then
        Sprint sprintNonCurrent = this.repository.findById(sprintId).get();
        assertThat(sprintNonCurrent.getIsCurrent()).isFalse();
        // Make it current again
        this.repository.setMaxNumSprintToCurrentByProjectId(this.projectId, this.sprintNumSprint);
        sprintNonCurrent = this.repository.findById(sprintId).get();
        assertThat(sprintNonCurrent.getIsCurrent()).isTrue();
    }*/
}
