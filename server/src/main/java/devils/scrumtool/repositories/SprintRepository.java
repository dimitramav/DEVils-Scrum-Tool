package devils.scrumtool.repositories;

import devils.scrumtool.entities.Sprint;
// Java libraries
import java.util.List;
import java.util.Optional;
// Spring libraries
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SprintRepository extends CrudRepository<Sprint, Integer> {
    // Query Methods
    Integer countByProjectId(Integer projectId);

    @Query("SELECT max(s.numSprint) FROM Sprint s WHERE s.project.id = :projectId")
    Integer getMaxNumSprintByProjectId(@Param("projectId") Integer projectId);

    Optional<Sprint> findByProject_IdAndIsCurrent(Integer projectId, Boolean isCurrent);

    List<Sprint> findByProject_IdOrderByDeadlineDateDesc(Integer projectId);

    @Modifying
    @Query(
            "UPDATE Sprint s SET s.isCurrent = FALSE WHERE s.project.id = :projectId AND s.isCurrent = TRUE")
    @Transactional
    void setLastCurrentSprintToNonCurrentByProjectId(@Param("projectId") Integer projectId);

    @Modifying
    @Query(
            "UPDATE Sprint s SET s.isCurrent = TRUE WHERE s.project.id = :projectId AND s.numSprint = :numSprint")
    @Transactional
    void setMaxNumSprintToCurrentByProjectId(
            @Param("projectId") Integer projectId, @Param("numSprint") Integer numSprint);

    @Transactional
    void deleteById(Integer sprintId);
}
