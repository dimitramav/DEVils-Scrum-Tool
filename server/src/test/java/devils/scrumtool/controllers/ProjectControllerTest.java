package devils.scrumtool.controllers;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import devils.scrumtool.entities.Project;
import devils.scrumtool.repositories.ProjectRepository;
// Java libraries
import java.sql.Date;
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
public class ProjectControllerTest {

    @Autowired private MockMvc mvc;
    // Retrieve objects from database (repositories functionality tested)
    @Autowired private ProjectRepository repository;
    // Map objects for post and put calls
    @Autowired private ObjectMapper mapper;

    @WithMockUser("spring")
    @Test
    @Order(1)
    public void getNumOfUserProjects() throws Exception {
        // /users/{userId}/numOfProjects
        String url = "/users/1/numOfProjects";
        mvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .param("isDone", "false")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @WithMockUser("spring")
    @Test
    @Order(2)
    public void getUserProjects() throws Exception {
        // /users/{userId}/projects
        String url = "/users/2/projects";
        mvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .param("isDone", "false")
                                .param("recordsPerPage", "4")
                                .param("currentPage", "1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.[*].title")
                                .value(containsInAnyOrder("test project 1", "test project 2")));
    }

    @WithMockUser("spring")
    @Test
    @Order(3)
    public void insertProject() throws Exception {
        // /users/{userId}/projects
        String url = "/users/2/projects";
        // Create the project object and map it as json string
        Date date = new Date(System.currentTimeMillis());
        Project newProject = new Project("springProject", false, date);
        String json = mapper.writeValueAsString(newProject);
        mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("springProject"))
                .andExpect(jsonPath("$.isDone").value("false"));
    }

    // Tasks and issues are added on the other controller tests
    @WithMockUser("spring")
    @Test
    @Order(4)
    public void getProjectOverview() throws Exception {
        // /users/{userId}/projects/{projectId}
        String url = "/users/1/projects/2";
        mvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .param("sprintId", "1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentSprintId").value("1"))
                .andExpect(jsonPath("$.currentSprintNum").value("1"))
                .andExpect(jsonPath("$.currentSprintGoal").value("testing"));
        // .andExpect(jsonPath("$.todo").value("1"))
        // .andExpect(jsonPath("$.doing").value("1"))
        // .andExpect(jsonPath("$.done").value("0"))
        // .andExpect(jsonPath("$.issues").value("1"));
    }

    @WithMockUser("spring")
    @Test
    @Order(5)
    public void updateProject() throws Exception {
        // /users/{userId}/projects/{projectId}
        String url = "/users/1/projects/3";
        // Get the project object from db, edit it and map it as json string
        Project editProject = this.repository.findById(3).get();
        editProject.setTitle("springProject123");
        String json = mapper.writeValueAsString(editProject);
        mvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("springProject123"))
                .andExpect(jsonPath("$.isDone").value("false"));
    }

    @WithMockUser("spring")
    @Test
    @Order(6)
    public void archiveActivateProject() throws Exception {
        // /users/{userId}/projects/{projectId}/changeIsDone
        String url = "/users/1/projects/3/changeIsDone";
        // Request body is a boolean (true -> archive project)
        String json = "true";
        mvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser("spring")
    @Test
    @Order(7)
    public void projectAuthorization() throws Exception {
        // /users/{userId}/projects/{projectId}/projectAuthorization
        String url = "/users/1/projects/2/projectAuthorization";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        // Project does not belong to this user
        url = "/users/1/projects/1/projectAuthorization";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
