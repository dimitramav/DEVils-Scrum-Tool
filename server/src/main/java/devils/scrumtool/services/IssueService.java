package devils.scrumtool.services;

import devils.scrumtool.entities.Issue;
import devils.scrumtool.repositories.IssueRepository;
// Java libraries
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueService {

    @Autowired private IssueRepository issueRepository;

    @Autowired private TaskService taskService;

    // Methods
    public Issue getIssueById(Integer issueId) throws Exception {
        Optional<Issue> dbIssue = issueRepository.findById(issueId);
        if (!dbIssue.isPresent()) {
            throw new Exception("Issue with id: " + issueId + " not found!");
        }
        return dbIssue.get();
    }

    public Integer getNumberOfTaskIssues(Integer taskId) {
        return issueRepository.countByTask_Id(taskId);
    }

    public Issue createOrEditIssue(Issue newIssue, Integer taskId) throws Exception {
        // First get the task that this issue belongs to
        newIssue.setTask(taskService.getTaskById(taskId));
        // Now save the Issue and return it
        return issueRepository.save(newIssue);
    }
}
