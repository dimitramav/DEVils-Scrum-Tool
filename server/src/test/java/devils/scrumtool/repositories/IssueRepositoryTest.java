package devils.scrumtool.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import devils.scrumtool.entities.Issue;
// Java libraries
import java.util.List;
// JUnit & Spring libraries
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class IssueRepositoryTest {

    @Autowired private IssueRepository repository;
    // given
    private int issueId = 1;
    private int taskId = 1;

    @Test
    void findIssuesOfTaskId() throws Exception {
        // given
        Issue testIssue = this.repository.findById(this.issueId).get();
        assertThat(testIssue.getDescription()).isEqualTo("Test Issue");
        // when
        int testTaskId = testIssue.getTask().getId();
        List<Issue> issuesOfTask = this.repository.findByTask_Id(testTaskId);
        // then
        assertThat(testTaskId).isEqualTo(this.taskId);
        assertThat(issuesOfTask.size()).isEqualTo(1);
        // assertThat(issuesOfTask.get(0)).usingRecursiveComparison().isEqualTo(testIssue);
    }

    @Test
    void countByTaskId() throws Exception {
        // when
        int numOfIssues = this.repository.countByTask_Id(this.taskId);
        // then
        assertThat(numOfIssues).isEqualTo(1);
    }
}
