package devils.scrumtool.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import devils.scrumtool.entities.User;
// Java libraries
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
// JUnit & Spring libraries
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired private UserRepository repository;
    // given
    private int userId = 2;
    private String username = "abcdef";
    private String email = "abcd@ef";

    @Test
    void getNumOfAllUsers() throws Exception {
        // when
        Iterable<User> users = this.repository.findAll();
        // then
        Iterator iterator = users.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertThat(count).isEqualTo(3);
    }

    @Test
    void existsByEmailOrUsername() throws Exception {
        // when
        Boolean existsUsername = this.repository.existsByUsername(this.username);
        Boolean existsEmail = this.repository.existsByEmail(this.email);
        // then
        assertThat(existsUsername).isTrue();
        assertThat(existsEmail).isTrue();
    }

    @Test
    void findByEmailOrUsername() throws Exception {
        // when
        Optional<User> userByUsername = this.repository.findByUsername(this.username);
        Optional<User> userByEmail = this.repository.findByEmail(this.email);
        // then
        assertThat(userByUsername.isPresent()).isTrue();
        assertThat(userByEmail.isPresent()).isTrue();
    }

    @Test
    void findUsersByProjectId() throws Exception {
        // given
        int projectId = 2;
        // when
        List<User> usersInProject = this.repository.findByProjects_ProjectId(projectId);
        // then
        assertThat(usersInProject.size()).isEqualTo(3);
    }
}
