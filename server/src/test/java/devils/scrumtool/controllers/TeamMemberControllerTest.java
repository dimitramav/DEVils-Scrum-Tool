package devils.scrumtool.controllers;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
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
public class TeamMemberControllerTest {

    @Autowired private MockMvc mvc;

    @WithMockUser("spring")
    @Test
    public void getProjectMembers() throws Exception {
        // /users/{userId}/projects/{projectId}/members
        String url = "/users/1/projects/2/members";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.[*].username")
                                .value(containsInAnyOrder("abcdef", "abcdefg", "abcdefgh")));
    }

    @WithMockUser("spring")
    @Test
    public void insertNewMemberIntoProject() throws Exception {
        // /users/{userId}/projects/{projectId}/members
        String url = "/users/1/projects/2/members";
        // Request body is the role of the member in project
        String json = "Product Owner";
        mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
