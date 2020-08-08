package devils.scrumtool.controllers;

import devils.scrumtool.entities.User;
import devils.scrumtool.exceptions.CustomException;
import devils.scrumtool.models.AuthenticationRequest;
import devils.scrumtool.models.Profile;
import devils.scrumtool.repositories.UserRepository;
import devils.scrumtool.services.UserService;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// User API
@RestController
// @RequestMapping("/users")
public class UserController {

    @Autowired private UserRepository userRepository;

    @Autowired private UserService userService;

    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/exists/username/{username}")
    public boolean existsUsername(@PathVariable String username) throws CustomException {
        return userRepository.existsByUsername(username);
    }

    @GetMapping("/exists/email/{email}")
    public boolean existsEmail(@PathVariable String email) throws CustomException {
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

    @PostMapping("/password/{userId}/matches")
    public boolean passwordMatches(
            @PathVariable Integer userId, @RequestBody AuthenticationRequest plainPassword)
            throws Exception {
        return userService.passwordOfUserIdMatches(userId, plainPassword.getPassword());
    }

    @PutMapping("/password/{userId}/update")
    public String passwordUpdate(
            @PathVariable Integer userId, @RequestBody AuthenticationRequest plainPassword)
            throws Exception {
        return userService.passwordOfUserIdUpdate(userId, plainPassword.getPassword());
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserAccount(@PathVariable Integer userId) throws Exception {
        userService.deleteUserAndRelations(userId);
    }
}
