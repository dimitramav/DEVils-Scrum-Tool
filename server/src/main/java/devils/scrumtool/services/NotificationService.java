package devils.scrumtool.services;

import devils.scrumtool.entities.Notification;
import devils.scrumtool.repositories.NotificationRepository;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired private NotificationRepository notificationRepository;

    @Autowired private ProjectService projectService;

    @Autowired private UserService userService;

    // Methods
    public void createNotification(Notification newNotification, Integer userId) throws Exception {
        // Now find the project into which the receiver is invited
        Integer projectId = newNotification.getProject().getId();
        newNotification.setProject(projectService.getProjectById(projectId));
        // Find the sender by userId
        newNotification.setSender(userService.getUserById(userId));
        // Case 1: Respond to product owner's invitation
        if (newNotification.getType().equals("Answer-Accept/Decline")) {
            // Find receiver by username
            String receiverUsername = newNotification.getReceiver().getUsername();
            newNotification.setReceiver(userService.getUserByUsername(receiverUsername));
        } // Case 2: Send an invitation as a product owner
        else if (newNotification.getType().equals("Accept/Decline")) {
            // Find receiver by email
            String receiverEmail = newNotification.getReceiver().getEmail();
            newNotification.setReceiver(userService.getUserByEmail(receiverEmail));
        }
        notificationRepository.save(newNotification);
    }
}
