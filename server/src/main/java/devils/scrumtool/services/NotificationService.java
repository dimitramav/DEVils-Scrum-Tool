package devils.scrumtool.services;

import devils.scrumtool.entities.Notification;
import devils.scrumtool.entities.Project;
import devils.scrumtool.entities.User;
import devils.scrumtool.repositories.NotificationRepository;
import devils.scrumtool.repositories.ProjectRepository;
import devils.scrumtool.repositories.UserRepository;
// Java libraries
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired private NotificationRepository notificationRepository;

    @Autowired private ProjectRepository projectRepository;

    @Autowired private UserRepository userRepository;

    public void createNotification(Notification newNotification, Integer userId) throws Exception {
        // Now find the project into which the receiver is invited
        Integer projectId = newNotification.getProject().getId();
        Optional<Project> dbProject = projectRepository.findById(projectId);
        if (!dbProject.isPresent()) {
            throw new Exception("Project with id: " + projectId + " not found!");
        }
        newNotification.setProject(dbProject.get());
        // Find the sender by userId
        Optional<User> dbUser = userRepository.findById(userId);
        if (!dbUser.isPresent()) {
            throw new Exception("User with id: " + userId + " not found!");
        }
        newNotification.setSender(dbUser.get());
        // Case 1: Respond to product owner's invitation
        if (newNotification.getType().equals("Answer-Accept/Decline")) {
            // Find receiver
            String receiverUsername = newNotification.getReceiver().getUsername();
            dbUser = userRepository.findByUsername(receiverUsername);
            if (!dbUser.isPresent()) {
                throw new Exception("User with username: " + receiverUsername + " not found!");
            }
            newNotification.setReceiver(dbUser.get());
        } // Case 2: Send an invitation as a product owner
        else if (newNotification.getType().equals("Accept/Decline")) {
            // Find receiver
            String receiverEmail = newNotification.getReceiver().getEmail();
            dbUser = userRepository.findByEmail(receiverEmail);
            if (!dbUser.isPresent()) {
                throw new Exception("User with Email: " + receiverEmail + " not found!");
            }
            newNotification.setReceiver(dbUser.get());
        }
        notificationRepository.save(newNotification);
    }
}
