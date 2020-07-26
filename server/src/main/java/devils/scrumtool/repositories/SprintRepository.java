package devils.scrumtool.repositories;

import devils.scrumtool.entities.Sprint;
// Java libraries
// import java.util.List;
// Spring libraries
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends CrudRepository<Sprint, Integer> {
    // Query Methods
}
