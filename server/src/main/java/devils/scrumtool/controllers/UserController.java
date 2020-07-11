package devils.scrumtool.controllers;

// Project's custom classes
import devils.scrumtool.entities.User;
import devils.scrumtool.models.AuthenticationRequest;
import devils.scrumtool.models.Profile;
import devils.scrumtool.repositories.UserRepository;
import devils.scrumtool.services.UserService;
// Java libraries
import java.util.List;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// User API
@RestController
// @RequestMapping("/users")
public class UserController {

    @Autowired private UserRepository userRepository;

    @Autowired private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/exists/username/{username}")
    public boolean existsUsername(@PathVariable String username) throws Exception {
        return userRepository.existsByUsername(username);
    }

    @GetMapping("/exists/email/{email}")
    public boolean existsEmail(@PathVariable String email) throws Exception {
        return userRepository.existsByEmail(email);
    }

    @PostMapping("/signup")
    public User insertUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @GetMapping("/profile/{username}")
    public Profile getUserProfile(@PathVariable String username) {
        return userService.getProfileByUsername(username);
    }

    @PutMapping("/profile/{username}")
    public Profile updateUserProfile(
            @PathVariable String username, @RequestBody Profile updatedProfile) throws Exception {
        return userService.updateProfileByUsername(username, updatedProfile);
    }

    @PostMapping("/password/{id}/matches")
    public boolean passwordMatches(
            @PathVariable Integer id, @RequestBody AuthenticationRequest plainPassword)
            throws Exception {
        return userService.passwordOfUserIdMatches(id, plainPassword.getPassword());
    }

    @PutMapping("/password/{id}/update")
    public String passwordUpdate(
            @PathVariable Integer id, @RequestBody AuthenticationRequest plainPassword)
            throws Exception {
        return userService.passwordOfUserIdUpdate(id, plainPassword.getPassword());
    }

    /*@PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
         @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employee.setEmailId(employeeDetails.getEmailId());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
         throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }*/
}
