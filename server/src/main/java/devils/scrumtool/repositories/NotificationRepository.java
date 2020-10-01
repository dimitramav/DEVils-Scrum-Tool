package devils.scrumtool.repositories;

import devils.scrumtool.entities.Notification;
// Java libraries
import java.util.List;
// Spring libraries
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {
    // Query Methods
    List<Notification> findByReceiver_Id(Integer userId);
}
