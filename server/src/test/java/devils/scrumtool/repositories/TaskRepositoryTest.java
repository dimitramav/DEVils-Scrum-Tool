package devils.scrumtool.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import devils.scrumtool.entities.Task;
// Java libraries
import java.util.List;
// JUnit & Spring libraries
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired private TaskRepository repository;
    // given
    private int taskId = 1;

    @Test
    void findTasksOfStoryId() throws Exception {
        // given
        Task testTask = this.repository.findById(this.taskId).get();
        assertThat(testTask.getDescription()).isEqualTo("Test Task 1");
        // when
        int storyId = testTask.getStory().getId();
        List<Task> tasksOfStory = this.repository.findByStory_Id(storyId);
        // then
        assertThat(tasksOfStory.size()).isEqualTo(2);
    }
}
