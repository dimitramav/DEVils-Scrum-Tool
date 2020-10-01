package devils.scrumtool.repositories;

import devils.scrumtool.entities.Project;
// Java libraries
import java.util.List;
// Spring libraries
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {
    // Query Methods
    Integer countByUsers_UserIdAndIsDone(Integer userId, Boolean isDone);

    List<Project> findByUsers_UserIdAndIsDone(Integer userId, Boolean isDone, Pageable paging);

    @Modifying
    @Query("UPDATE Project p SET p.isDone = :isDone WHERE p.id = :id")
    @Transactional
    void archiveOrActivateProjectById(
            @Param("isDone") Boolean isDoneToBecome, @Param("id") Integer projectId);

    @Transactional
    void deleteById(Integer projectId);
}
