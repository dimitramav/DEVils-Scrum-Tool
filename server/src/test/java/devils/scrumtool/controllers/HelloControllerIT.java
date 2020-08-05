package devils.scrumtool.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HelloControllerIT {

    @LocalServerPort private int port;

    private URL base;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private String controllerMapping = "/hello";

    @Autowired Environment env;

    @Autowired private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + contextPath + controllerMapping);
    }

    @Test
    public void loadTestProperties() throws Exception {
        assertThat(env.getProperty("spring.datasource.username")).isEqualTo("sa");
        assertThat(env.getProperty("spring.datasource.password")).isEqualTo("");
        assertThat(contextPath).isEqualTo("/api");
    }

    @Test
    public void getHelloAssertJ() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertThat(response.getBody()).isEqualTo("Greetings from Spring Boot!");
    }

    @Test
    public void getHelloJUnit() throws Exception {
        String body = this.template.getForObject(controllerMapping, String.class);
        assertEquals(body, "Greetings from Spring Boot!");
    }
}
