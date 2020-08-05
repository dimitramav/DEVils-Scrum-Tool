package devils.scrumtool.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import devils.scrumtool.entities.Notification;
// Java libraries
import java.util.List;
// JUnit & Spring libraries
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class NotificationRepositoryTest {

    @Autowired private NotificationRepository repository;
    // given
    private int userId = 1;

    @Test
    void findByReceiverId() throws Exception {
        // when
        List<Notification> notifs = this.repository.findByReceiver_Id(this.userId);
        // then
        assertThat(notifs.size()).isEqualTo(0); // No notifications on database
    }
}
