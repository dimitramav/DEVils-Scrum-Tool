package devils.scrumtool.repositories;

import devils.scrumtool.entities.Issue;
// Java libraries
import java.util.List;
// Spring libraries
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Integer> {
    // Query Methods
    List<Issue> findByTask_Id(Integer taskId);

    Integer countByTask_Id(Integer taskId);

    @Transactional
    void deleteById(Integer issueId);
}
