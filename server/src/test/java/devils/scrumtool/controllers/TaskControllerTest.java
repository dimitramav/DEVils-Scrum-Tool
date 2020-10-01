package devils.scrumtool.controllers;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import devils.scrumtool.entities.Task;
import devils.scrumtool.repositories.TaskRepository;
// JUnit & Spring libraries
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class TaskControllerTest {

    @Autowired private MockMvc mvc;
    // Retrieve objects from database (repositories functionality tested)
    @Autowired private TaskRepository repository;
    // Map objects for post and put calls
    @Autowired private ObjectMapper mapper;

    @WithMockUser("spring")
    @Test
    @Order(1)
    public void getSprintTasks() throws Exception {
        // /users/{userId}/projects/{projectId}/tasks
        String url = "/users/2/projects/2/tasks";
        mvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .param("sprintId", "1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.[*].description")
                                .value(containsInAnyOrder("Test Task 1", "Test Task 2")));
    }

    @WithMockUser("spring")
    @Test
    @Order(2)
    public void insertTask() throws Exception {
        // /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks
        String url = "/users/2/projects/2/pbis/2/tasks";
        // Create the task object and map it as json string
        Task newTask = new Task("springTask", 1);
        String json = mapper.writeValueAsString(newTask);
        mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.description").value("springTask"))
                .andExpect(jsonPath("$.state").value(1))
                .andExpect(jsonPath("$.storyId").value(2));
    }

    @WithMockUser("spring")
    @Test
    @Order(3)
    public void updateTask() throws Exception {
        // /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks
        String url = "/users/2/projects/2/pbis/2/tasks";
        // Create the task object and map it as json string
        Task editTask = this.repository.findById(3).get();
        editTask.setDescription("springTask22");
        editTask.setState(3);
        String json = mapper.writeValueAsString(editTask);
        mvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.description").value("springTask22"))
                .andExpect(jsonPath("$.state").value(3))
                .andExpect(jsonPath("$.storyId").value(2));
    }
}
