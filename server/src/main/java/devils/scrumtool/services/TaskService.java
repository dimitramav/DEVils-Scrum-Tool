package devils.scrumtool.services;

import devils.scrumtool.entities.Task;
import devils.scrumtool.models.SprintStory;
import devils.scrumtool.models.TaskDTO;
import devils.scrumtool.repositories.TaskRepository;
// Java libraries
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired private TaskRepository taskRepository;

    @Autowired private PBIService pbiService;

    // Methods
    public Task getTaskById(Integer taskId) throws Exception {
        Optional<Task> dbTask = taskRepository.findById(taskId);
        if (!dbTask.isPresent()) {
            throw new Exception("Task with id: " + taskId + " not found!");
        }
        return dbTask.get();
    }

    public List<Task> getTasksByStoryIdWithSprintId(Integer sprintId) {
        // Create a list to store all the tasks that belong this sprint's stories
        List<Task> sprintTasks = new ArrayList<Task>();
        // Find all the pbis of this sprint
        List<SprintStory> sprintStories = pbiService.getPBIsBySprintIdAsSprintStories(sprintId);
        // Get all tasks attached to each story and add them to the sprintTasks list
        for (int i = 0; i < sprintStories.size(); i++) {
            sprintTasks.addAll(taskRepository.findByStory_Id(sprintStories.get(i).getIdPBI()));
        } // Return the list
        return sprintTasks;
    }

    public List<TaskDTO> getTasksDTOByStoryIdWithSprintId(Integer sprintId) {
        // Create a list to store all the tasks that belong this sprint's stories
        List<Task> sprintTasks = this.getTasksByStoryIdWithSprintId(sprintId);
        // Convert all sprint tasks into a list of TaskDTOs
        List<TaskDTO> sprintTasksDTO = new ArrayList<TaskDTO>();
        for (int i = 0; i < sprintTasks.size(); i++) {
            sprintTasksDTO.add(new TaskDTO(sprintTasks.get(i)));
        } // Return the dto
        return sprintTasksDTO;
    }

    public TaskDTO createOrEditTask(Task newTask, Integer pbiId) throws Exception {
        // First get the story (pbi) that this task belongs to
        newTask.setStory(pbiService.getPBIById(pbiId));
        // Now save the Task
        Task editedTask = taskRepository.save(newTask);
        // Return a task dto
        return new TaskDTO(editedTask);
    }
}
