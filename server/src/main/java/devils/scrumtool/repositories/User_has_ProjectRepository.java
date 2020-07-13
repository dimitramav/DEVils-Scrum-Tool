package devils.scrumtool.repositories;

import devils.scrumtool.entities.User_has_Project;
import devils.scrumtool.entities.User_has_Project.UserHasProjectId;
// Java libraries
// import java.util.List;
// Spring libraries
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User_has_ProjectRepository
        extends CrudRepository<User_has_Project, UserHasProjectId> {
    // Query Methods
}
