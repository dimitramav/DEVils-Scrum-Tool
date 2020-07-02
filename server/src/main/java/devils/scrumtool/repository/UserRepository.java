package devils.scrumtool.repository;

import devils.scrumtool.model.User;
// Java libraries
import java.util.Optional;
// Spring libraries
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Custom Query Methods
    Optional<User> getUserById(Integer id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
