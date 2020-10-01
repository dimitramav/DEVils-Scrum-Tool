package devils.scrumtool.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import devils.scrumtool.entities.User;
import devils.scrumtool.models.AuthenticationRequest;
import devils.scrumtool.repositories.UserRepository;
// JUnit & Spring libraries
import org.hamcrest.core.IsNull;
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
public class UserControllerTest {

    @Autowired private MockMvc mvc;
    // Retrieve objects from database (repositories functionality tested)
    @Autowired private UserRepository repository;
    // Map objects for post and put calls
    @Autowired private ObjectMapper mapper;

    @Test
    @Order(1)
    public void existsUsername() throws Exception {
        // /exists/username/{username}
        String url = "/exists/username/abcdefgh";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @Order(2)
    public void existsEmail() throws Exception {
        // /exists/email/{email}
        String url = "/exists/email/abcd@ef";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @Order(3)
    public void insertUser() throws Exception {
        // /signup
        String url = "/signup";
        // Create the user object and map it as json string
        User newUser = new User("testspring", "test@spring", "test", "spring", "secret1!");
        String json = mapper.writeValueAsString(newUser);
        mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testspring"))
                .andExpect(jsonPath("$.email").value("test@spring"))
                .andExpect(jsonPath("$.firstname").value("test"))
                .andExpect(jsonPath("$.lastname").value("spring"))
                .andExpect(jsonPath("$.country").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.isAdmin").value(false))
                .andExpect(jsonPath("$.numProjects").value(0));
    }

    @WithMockUser("spring")
    @Test
    @Order(4)
    public void getUserProfile() throws Exception {
        // /profile/{username}
        String url = "/profile/abcdefg";
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("abcd@efg"))
                .andExpect(jsonPath("$.firstname").value("abcd"))
                .andExpect(jsonPath("$.lastname").value("efg"));
    }

    @WithMockUser("spring")
    @Test
    @Order(5)
    public void updateUserProfile() throws Exception {
        // /profile/{username}
        String url = "/profile/abcdefg";
        // Get the user object from db, edit it and map it as json string
        User editUser = this.repository.findByUsername("abcdefg").get();
        editUser.setLastname("ef1");
        editUser.setCountry("Greece");
        String json = mapper.writeValueAsString(editUser);
        mvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("abcd@efg"))
                .andExpect(jsonPath("$.firstname").value("abcd"))
                .andExpect(jsonPath("$.lastname").value("ef1"))
                .andExpect(jsonPath("$.country").value("Greece"));
    }

    @WithMockUser("spring")
    @Test
    @Order(6)
    public void passwordMatches() throws Exception {
        // /password/{userId}/matches
        String url = "/password/1/matches";
        // Create the object and map it as json string
        AuthenticationRequest pass = new AuthenticationRequest("abcdefg", "abcd1234!");
        String json = mapper.writeValueAsString(pass);
        mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @WithMockUser("spring")
    @Test
    @Order(7)
    public void passwordUpdate() throws Exception {
        // /password/{userId}/update
        String url = "/password/1/update";
        // Create the object and map it as json string
        AuthenticationRequest pass = new AuthenticationRequest("abcdefg", "abcd1234#");
        String json = mapper.writeValueAsString(pass);
        mvc.perform(
                        MockMvcRequestBuilders.put(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
