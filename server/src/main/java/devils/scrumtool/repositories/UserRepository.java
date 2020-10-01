package devils.scrumtool.repositories;

import devils.scrumtool.entities.User;
// Java libraries
import java.util.List;
import java.util.Optional;
// Spring libraries
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    // Query Methods
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByProjects_ProjectId(Integer projectId);

    @Transactional
    void deleteById(Integer userId);
}
