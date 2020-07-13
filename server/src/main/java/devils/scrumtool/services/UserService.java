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
        Optional<User> dbUser = userRepository.findByUsername(username);
        if (!dbUser.isPresent()) {
            throw new Exception("User with username: " + username + " not found!");
        }
        User userToUpdate = dbUser.get();
        userToUpdate.updateUserProfile(profile);
        userRepository.save(userToUpdate);
        return profile;
    }

    public boolean passwordOfUserIdMatches(Integer userId, String plainPassword) throws Exception {
        Optional<User> dbUser = userRepository.findById(userId);
        if (!dbUser.isPresent()) {
            throw new Exception("User with id: " + userId + " not found!");
        }
        String dbPassword = dbUser.get().getPassword();
        return passwordEncoder.matches(plainPassword, dbPassword);
    }

    public String passwordOfUserIdUpdate(Integer userId, String plainPassword) throws Exception {
        Optional<User> dbUser = userRepository.findById(userId);
        if (!dbUser.isPresent()) {
            throw new Exception("User with id: " + userId + " not found!");
        }
        String newPassword = passwordEncoder.encode(plainPassword);
        User updatedUser = dbUser.get();
        updatedUser.setPassword(newPassword);
        updatedUser = userRepository.save(updatedUser);
        if (updatedUser == null) {
            throw new Exception("User with id: " + userId + " not updated!");
        }
        return newPassword;
    }

    /*@Override
    public boolean findUser(String username) {
    	return dao.findByUsername(username) != null ? true : false;
    }

    @Override
    public UserDTO findUserByUsername(String username) {
    	return dao.findByUsername(username);
    }

    @Override
    public void updateUser(Long id, String firstname, String lastname, String email, String company, String role,
    		String summary) {
    	UserDTO userToUpdate = dao.getOne(id);
    	userToUpdate.setFirstname(firstname);
    	userToUpdate.setLastname(lastname);
    	userToUpdate.setEmail(email);
    	userToUpdate.setCompany(company);
    	userToUpdate.setProfession(role);
    	userToUpdate.setSummary(summary);
    	dao.save(userToUpdate);
    }

    @Override
    public boolean updatePassword(Long id, String oldPassword, String newPassword) {
    	UserDTO userToUpdate = dao.findById(id).get();
    	if (userToUpdate == null)
    		return false;
    	if (passwordEncoder.matches(oldPassword, userToUpdate.getPassword())) {
    		userToUpdate.setPassword(passwordEncoder.encode(newPassword));
    		dao.save(userToUpdate);
    		return true;
    	}
    	return false;
    }*/
}
