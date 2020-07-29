package devils.scrumtool.controllers;

import devils.scrumtool.entities.Issue;
import devils.scrumtool.repositories.IssueRepository;
import devils.scrumtool.services.IssueService;
// Java libraries
import java.util.List;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Issue API
@RestController
public class IssueController {

    @Autowired private IssueRepository issueRepository;

    @Autowired private IssueService issueService;

    @GetMapping("/issues")
    public Iterable<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @GetMapping("/users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}/issues")
    public List<Issue> getTaskIssues(@PathVariable Integer taskId) {
        return issueRepository.findByTask_Id(taskId);
    }

    @PostMapping("/users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}/issues")
    public Issue insertIssue(@RequestBody Issue newIssue, @PathVariable Integer taskId)
            throws Exception {
        return issueService.createOrEditIssue(newIssue, taskId);
    }

    @PutMapping("/users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}/issues")
    public Issue updateIssue(@RequestBody Issue editedIssue, @PathVariable Integer taskId)
            throws Exception {
        return issueService.createOrEditIssue(editedIssue, taskId);
    }

    @DeleteMapping(
            "/users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}/issues/{issueId}")
    public void deleteIssue(@PathVariable Integer issueId) {
        issueRepository.deleteById(issueId);
    }
}
