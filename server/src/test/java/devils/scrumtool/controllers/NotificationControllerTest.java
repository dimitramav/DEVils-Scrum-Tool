package devils.scrumtool.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import devils.scrumtool.entities.Notification;
import devils.scrumtool.entities.User;
import devils.scrumtool.repositories.ProjectRepository;
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
public class NotificationControllerTest {

    @Autowired private MockMvc mvc;
    // Retrieve objects from database (repositories functionality tested)
    @Autowired private ProjectRepository projectRepository;
    // Map objects for post and put calls
    @Autowired private ObjectMapper mapper;

    @WithMockUser("spring")
    @Test
    @Order(1)
    public void insertProjectNotification() throws Exception {
        // /users/{userId}/notifications
        String url = "/users/2/notifications";
        // Create the notification object and map it as json string
        Notification newNotification =
                new Notification(
                        "test project 1", "Developer", "Accept/Decline", "test notification");
        newNotification.setReceiver(new User("abcdefg", "abcd@efg", "a", "b", "c"));
        newNotification.setProject(this.projectRepository.findById(1).get());
        // Mock the post request
        String json = mapper.writeValueAsString(newNotification);
        mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser("spring")
    @Test
    @Order(2)
    public void getUserNotifications() throws Exception {
        // /users/{userId}/notifications
        String url = "/users/1/notifications";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].projectTitle").value("test project 1"))
                .andExpect(jsonPath("$.[0].role").value("Developer"))
                .andExpect(jsonPath("$.[0].type").value("Accept/Decline"))
                .andExpect(jsonPath("$.[0].message").value("test notification"));
    }

    // On delete request, http response "Accepted" is send from server
    @WithMockUser("spring")
    @Test
    @Order(3)
    public void deleteNotification() throws Exception {
        // /users/{userId}/notifications/{notificationId}
        String url = "/users/1/notifications/1";
        mvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(status().isAccepted());
    }
}
