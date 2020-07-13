package devils.scrumtool.repositories;

import devils.scrumtool.entities.Project;
// Java libraries
import java.util.List;
// Spring libraries
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {
    // Query Methods
    Integer countByUsers_UserIdAndIsDone(Integer userId, Boolean isDone);

    List<Project> findByUsers_UserIdAndIsDone(Integer userId, Boolean isDone, Pageable paging);
}
