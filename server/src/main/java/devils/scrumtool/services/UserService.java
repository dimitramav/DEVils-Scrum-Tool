package devils.scrumtool.services;

import devils.scrumtool.entities.User;
import devils.scrumtool.entities.User_has_Project;
import devils.scrumtool.models.Profile;
import devils.scrumtool.repositories.UserRepository;
// Java libraries
import java.util.List;
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

    @Autowired private TeamMemberService teamMemberService;

    @Autowired private ProjectService projectService;

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

    @Transactional
    public void deleteUserAndRelations(Integer userId) throws Exception {
        // First get all the relations that current user has with projects
        List<User_has_Project> userRelations = teamMemberService.getRelationsByUserId(userId);
        // Delete all user's relation with projects
        for (int i = 0; i < userRelations.size(); i++) {
            int projectId = userRelations.get(i).getProject().getId();
            // Delete all projects in which current user was Product Owner
            if (userRelations.get(i).getRole().equals("Product Owner")) {
                projectService.deleteProjectAndRelations(projectId);
            } // Else just delete the relation (just like leaving the project)
            teamMemberService.deleteMemberFromProject(userId, projectId);
        } // Finally delete the user
        userRepository.deleteById(userId);
    }
}
