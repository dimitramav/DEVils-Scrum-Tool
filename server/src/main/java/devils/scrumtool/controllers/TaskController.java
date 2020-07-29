package devils.scrumtool.controllers;

import devils.scrumtool.entities.Task;
import devils.scrumtool.models.TaskDTO;
import devils.scrumtool.repositories.TaskRepository;
import devils.scrumtool.services.TaskService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Task API
@RestController
public class TaskController {

    @Autowired private TaskRepository taskRepository;

    @Autowired private TaskService taskService;

    @GetMapping("/tasks")
    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/users/{userId}/projects/{projectId}/tasks")
    public List<TaskDTO> getSprintTasks(@RequestParam Integer sprintId) {
        return taskService.getTasksDTOByStoryIdWithSprintId(sprintId);
    }

    @PostMapping("/users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks")
    public TaskDTO insertTask(@RequestBody Task newTask, @PathVariable Integer pbiId)
            throws Exception {
        return taskService.createOrEditTask(newTask, pbiId);
    }

    @PutMapping("/users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks")
    public TaskDTO updateTask(@RequestBody Task editedTask, @PathVariable Integer pbiId)
            throws Exception {
        return taskService.createOrEditTask(editedTask, pbiId);
    }

    @DeleteMapping("/users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}")
    public void deleteTask(@PathVariable Integer taskId) {
        taskRepository.deleteById(taskId);
    }
}
