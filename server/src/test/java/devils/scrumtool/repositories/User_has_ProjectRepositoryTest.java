package devils.scrumtool.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import devils.scrumtool.entities.User_has_Project;
// Java libraries
import java.util.List;
// JUnit & Spring libraries
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class User_has_ProjectRepositoryTest {

    @Autowired private User_has_ProjectRepository repository;
    // given
    private int userId = 2;
    private int projectId = 1;

    @Test
    void findByUserIdAndProjectId() throws Exception {
        // when
        User_has_Project relation =
                this.repository.findByUserIdAndProjectId(this.userId, this.projectId).get();
        // then
        assertThat(relation.getId().getUserId()).isEqualTo(this.userId);
        assertThat(relation.getId().getProjectId()).isEqualTo(this.projectId);
    }

    @Test
    void findByUserId() throws Exception {
        // when
        List<User_has_Project> relations = this.repository.findByUserId(this.userId);
        // then
        assertThat(relations.size()).isEqualTo(2);
    }
}
