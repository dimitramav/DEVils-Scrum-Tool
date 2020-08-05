package devils.scrumtool.repositories;

import devils.scrumtool.entities.PBI;
// Java libraries
import java.util.List;
// Spring libraries
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PBIRepository extends CrudRepository<PBI, Integer> {
    // Query Methods
    List<PBI> findByProject_IdAndIsEpic(Integer projectId, Boolean isEpic);

    List<PBI> findByProject_IdAndEpicId(Integer projectId, Integer epicId);

    List<PBI> findBySprint_Id(Integer sprintId);

    @Transactional
    void deleteById(Integer pbiId);
}
