package devils.scrumtool.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import devils.scrumtool.entities.Project;
// Java libraries
import java.util.List;
// JUnit & Spring libraries
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
public class ProjectRepositoryTest {

    @Autowired private ProjectRepository repository;
    // given
    private int projectId = 1;
    private int userId = 2;

    @Test
    @Order(1)
    void countActiveProjectsByUserId() throws Exception {
        // when
        int numOfActiveProjects = this.repository.countByUsers_UserIdAndIsDone(userId, false);
        // then
        assertThat(numOfActiveProjects).isEqualTo(2);
    }

    @Test
    @Order(2)
    void findActiveProjectsByUserId() throws Exception {
        // given
        Sort sortType = Sort.by("deadlineDate").ascending();
        Pageable paging = PageRequest.of(0, 4, sortType);
        // when
        List<Project> testProject =
                this.repository.findByUsers_UserIdAndIsDone(userId, false, paging);
        // then
        assertThat(testProject.size()).isEqualTo(2);
        assertThat(testProject.get(0).getTitle()).isEqualTo("test project 1");
        assertThat(testProject.get(1).getTitle()).isEqualTo("test project 2");
    }

    @Test
    @Order(3)
    void archiveProjectById() throws Exception {
        // given
        Project testProject = this.repository.findById(projectId).get();
        // when
        this.repository.archiveOrActivateProjectById(true, testProject.getId());
        // then
        Project projectArchived = this.repository.findById(projectId).get();
        assertThat(projectArchived.getIsDone()).isTrue();
    }

    @Test
    @Order(4)
    void countActiveProjectsByUserIdAfterArchiveProject() throws Exception {
        // when
        int numOfActiveProjects = this.repository.countByUsers_UserIdAndIsDone(userId, false);
        // then
        assertThat(numOfActiveProjects).isEqualTo(1);
    }
}
