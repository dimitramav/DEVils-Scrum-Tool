package devils.scrumtool.repositories;

import devils.scrumtool.entities.User;
// Java libraries
import java.util.Optional;
// Spring libraries
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    // Query Methods
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
