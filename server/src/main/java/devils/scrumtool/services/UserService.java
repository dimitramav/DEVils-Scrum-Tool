package devils.scrumtool.services;

import devils.scrumtool.entities.User;
import devils.scrumtool.models.Profile;
import devils.scrumtool.repositories.UserRepository;
// Java libraries
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    // Methods
    public User getUserById(Integer userId) throws Exception {
        Optional<User> dbUser = userRepository.findById(userId);
        if (!dbUser.isPresent()) {
            throw new Exception("User with id: " + userId + " not found!");
        }
        return dbUser.get();
    }

    public User getUserByUsername(String username) throws Exception {
        Optional<User> dbUser = userRepository.findByUsername(username);
        if (!dbUser.isPresent()) {
            throw new Exception("User with username: " + username + " not found!");
        }
        return dbUser.get();
    }

    public User getUserByEmail(String email) throws Exception {
        Optional<User> dbUser = userRepository.findByEmail(email);
        if (!dbUser.isPresent()) {
            throw new Exception("User with email: " + email + " not found!");
        }
        return dbUser.get();
    }

    @Transactional
    public User createUser(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setIsAdmin(false);
        newUser.setNumProjects(0);
        return userRepository.save(newUser);
    }

    public Profile getProfileByUsername(String username) {
        Optional<User> dbUser = userRepository.findByUsername(username);
        return new Profile(dbUser.get());
    }

    @Transactional
    public Profile updateProfileByUsername(String username, Profile profile) throws Exception {
        User userToUpdate = this.getUserByUsername(username);
        userToUpdate.updateUserProfile(profile);
        userRepository.save(userToUpdate);
        return profile;
    }

    public boolean passwordOfUserIdMatches(Integer userId, String plainPassword) throws Exception {
        String dbPassword = this.getUserById(userId).getPassword();
        return passwordEncoder.matches(plainPassword, dbPassword);
    }

    public String passwordOfUserIdUpdate(Integer userId, String plainPassword) throws Exception {
        String newPassword = passwordEncoder.encode(plainPassword);
        User updatedUser = this.getUserById(userId);
        updatedUser.setPassword(newPassword);
        updatedUser = userRepository.save(updatedUser);
        if (updatedUser == null) {
            throw new Exception("User with id: " + userId + " not updated!");
        }
        return newPassword;
    }
}
