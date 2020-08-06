package devils.scrumtool.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import devils.scrumtool.entities.PBI;
// Java libraries
import java.util.List;
// JUnit & Spring libraries
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class PBIRepositoryTest {

    @Autowired private PBIRepository repository;
    // given
    private int projectId = 2;
    private int sprintId = 1;
    private int epicId = 1;
    private int storyId = 2;

    @Test
    void findEpicsByProjectId() throws Exception {
        // when
        List<PBI> epicsInProject = this.repository.findByProject_IdAndIsEpic(this.projectId, true);
        // then
        assertThat(epicsInProject.size()).isEqualTo(1);
        assertThat(epicsInProject.get(0).getTitle()).isEqualTo("Test Epic");
    }

    @Test
    void findStoriesByProjectIdAndEpicId() throws Exception {
        // when
        List<PBI> storiesInEpic =
                this.repository.findByProject_IdAndEpicId(this.projectId, this.epicId);
        // then
        assertThat(storiesInEpic.size()).isEqualTo(2);
    }

    @Test
    void findSprintStories() throws Exception {
        // when
        List<PBI> storiesInSprint = this.repository.findBySprint_Id(this.sprintId);
        // then
        assertThat(storiesInSprint.size()).isEqualTo(1);
        assertThat(storiesInSprint.get(0).getTitle()).isEqualTo("Test Story 1");
    }
}
