package devils.scrumtool.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import devils.scrumtool.models.AuthenticationRequest;
// JUnit & Spring libraries
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc // (addFilters = false) // Exclude spring security filters
public class AuthenticationControllerTest {

    @Autowired private MockMvc mvc;
    // Map objects for post and put calls
    @Autowired private ObjectMapper mapper;

    @Test
    public void createAuthenticationToken() throws Exception {
        // /authenticate
        String url = "/authenticate";
        // Create an object with username and password and map it as json string
        AuthenticationRequest auth = new AuthenticationRequest("abcd@efg", "abcd1234!");
        String json = mapper.writeValueAsString(auth);
        mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("abcdefg"));
        // Test with non-existing user
        auth = new AuthenticationRequest("fdsgdfgd", "sdgdfg");
        json = mapper.writeValueAsString(auth);
        mvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.serverErrorMessage").value("Incorrect username or password"));
    }

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }
}
