package devils.scrumtool.controllers;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import devils.scrumtool.entities.PBI;
import devils.scrumtool.repositories.PBIRepository;
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
public class PBIControllerTest {

    @Autowired private MockMvc mvc;
    // Retrieve objects from database (repositories functionality tested)
    @Autowired private PBIRepository repository;
    // Map objects for post and put calls
    @Autowired private ObjectMapper mapper;

    @WithMockUser("spring")
    @Test
    @Order(1)
    public void getProjectPBIs() throws Exception {
        // /users/{userId}/projects/{projectId}/pbis
        String url = "/users/2/projects/2/pbis";
        mvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .param("isEpic", "true")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].title").value(containsInAnyOrder("Test Epic")));
    }

    @WithMockUser("spring")
    @Test
    @Order(2)
    public void insertPBI() throws Exception {
        // /users/{userId}/projects/{projectId}/pbis
        String url = "/users/2/projects/2/pbis";
        // Create the pbi object and map it as json string
        PBI newEpic = new PBI("springEpic", "new epic", 2, true);
        String json = mapper.writeValueAsString(newEpic);
        mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.title").value("springEpic"))
                .andExpect(jsonPath("$.description").value("new epic"))
                .andExpect(jsonPath("$.priority").value(2))
                .andExpect(jsonPath("$.isEpic").value(true));
    }

    @WithMockUser("spring")
    @Test
    @Order(3)
    public void updatePBI() throws Exception {
        // /users/{userId}/projects/{projectId}/pbis
        String url = "/users/2/projects/2/pbis";
        // Create the pbi object and map it as json string
        PBI editEpic = this.repository.findById(4).get();
        editEpic.setTitle("springEpic2");
        editEpic.setDescription("new epic 22");
        editEpic.setPriority(3);
        String json = mapper.writeValueAsString(editEpic);
        mvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.title").value("springEpic2"))
                .andExpect(jsonPath("$.description").value("new epic 22"))
                .andExpect(jsonPath("$.priority").value(3))
                .andExpect(jsonPath("$.isEpic").value(true));
    }

    @WithMockUser("spring")
    @Test
    @Order(4)
    public void getProjectSprintStories() throws Exception {
        // /users/{userId}/projects/{projectId}/sprintStories
        String url = "/users/2/projects/2/sprintStories";
        mvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .param("sprintId", "1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].title").value(containsInAnyOrder("Test Story 1")));
    }
}
