package devils.scrumtool.repositories;

import devils.scrumtool.entities.Task;
// Java libraries
import java.util.List;
// Spring libraries
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    // Query Methods
    List<Task> findByStory_Id(Integer pbiId);

    @Transactional
    void deleteById(Integer taskId);
}
