package devils.scrumtool.controllers;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import devils.scrumtool.entities.Issue;
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
public class IssueControllerTest {

    @Autowired private MockMvc mvc;
    // Map objects for post and put calls
    @Autowired private ObjectMapper mapper;

    @WithMockUser("spring")
    @Test
    @Order(1)
    public void getTaskIssues() throws Exception {
        // /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}/issues
        String url = "/users/2/projects/2/pbis/2/tasks/1/issues";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].description").value(containsInAnyOrder("Test Issue")));
    }

    @WithMockUser("spring")
    @Test
    @Order(2)
    public void insertIssue() throws Exception {
        // /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}/issues
        String url = "/users/2/projects/2/pbis/2/tasks/1/issues";
        // Create the issue object and map it as json string
        Issue newIssue = new Issue("springIssue");
        String json = mapper.writeValueAsString(newIssue);
        mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.description").value("springIssue"));
    }

    @WithMockUser("spring")
    @Test
    @Order(3)
    public void updateIssue() throws Exception {
        // /users/{userId}/projects/{projectId}/pbis/{pbiId}/tasks/{taskId}/issues
        String url = "/users/2/projects/2/pbis/2/tasks/1/issues";
        // Create the issue object and map it as json string
        Issue editIssue = new Issue("springIssue22");
        editIssue.setId(2);
        String json = mapper.writeValueAsString(editIssue);
        mvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.description").value("springIssue22"));
    }
}
