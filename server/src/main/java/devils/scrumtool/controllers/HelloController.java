package devils.scrumtool.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Dummy Controller for testing purposes
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
