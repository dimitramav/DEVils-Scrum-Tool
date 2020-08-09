package devils.scrumtool.controllers;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import devils.scrumtool.entities.Sprint;
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
public class SprintControllerTest {

    @Autowired private MockMvc mvc;
    // Map objects for post and put calls
    @Autowired private ObjectMapper mapper;

    @WithMockUser("spring")
    @Test
    @Order(1)
    public void getProjectSprints() throws Exception {
        // /users/{userId}/projects/{projectId}/sprints
        String url = "/users/2/projects/2/sprints";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].goal").value(containsInAnyOrder("testing")))
                .andExpect(jsonPath("$.[0].plan").value("Test Sprint"))
                .andExpect(jsonPath("$.[0].isCurrent").value(true));
    }

    @WithMockUser("spring")
    @Test
    @Order(2)
    public void insertSprint() throws Exception {
        // /users/{userId}/projects/{projectId}/sprints
        String url = "/users/2/projects/2/sprints";
        // Create the sprint object and map it as json string
        Date date = new Date(System.currentTimeMillis());
        Sprint newSprint = new Sprint(date, "test", "springSprint", true);
        String json = mapper.writeValueAsString(newSprint);
        mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value("test"))
                .andExpect(jsonPath("$.plan").value("springSprint"))
                .andExpect(jsonPath("$.isCurrent").value(true))
                .andExpect(jsonPath("$.numSprint").value(2));
    }

    @WithMockUser("spring")
    @Test
    @Order(3)
    public void getSprint() throws Exception {
        // /users/{userId}/projects/{projectId}/sprints/{sprintId}
        String url = "/users/2/projects/2/sprints/2";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value("test"))
                .andExpect(jsonPath("$.plan").value("springSprint"))
                .andExpect(jsonPath("$.isCurrent").value(true))
                .andExpect(jsonPath("$.numSprint").value(2));
    }

    @WithMockUser("spring")
    @Test
    @Order(4)
    public void updateSprint() throws Exception {
        // /users/{userId}/projects/{projectId}/sprints/{sprintId}
        String url = "/users/2/projects/2/sprints/2";
        // Create the sprint object and map it as json string
        Date date = new Date(System.currentTimeMillis());
        Sprint newSprint = new Sprint(date, "test2", "springSprint222", true);
        String json = mapper.writeValueAsString(newSprint);
        mvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value("test2"))
                .andExpect(jsonPath("$.plan").value("springSprint222"))
                .andExpect(jsonPath("$.isCurrent").value(true))
                .andExpect(jsonPath("$.numSprint").value(2));
    }

    @WithMockUser("spring")
    @Test
    @Order(5)
    public void updateSprintToCurrent() throws Exception {
        // /users/{userId}/projects/{projectId}/sprints/{sprintId}/makeSprintCurrent
        String url = "/users/2/projects/2/sprints/1/makeSprintCurrent";
        // Create the sprint object and map it as json string
        Date date = new Date(System.currentTimeMillis());
        Sprint newSprint = new Sprint(date, "test", "springSprint", true);
        String json = mapper.writeValueAsString(newSprint);
        mvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goal").value("testing"))
                .andExpect(jsonPath("$.plan").value("Test Sprint"))
                .andExpect(jsonPath("$.isCurrent").value(true))
                .andExpect(jsonPath("$.numSprint").value(1));
    }

    @WithMockUser("spring")
    @Test
    @Order(6)
    public void deleteSprint() throws Exception {
        // /users/{userId}/projects/{projectId}/sprints/{sprintId}
        String url = "/users/2/projects/2/sprints/2";
        mvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(status().isOk());
    }
}
