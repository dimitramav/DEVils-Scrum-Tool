package devils.scrumtool.controllers;

import devils.scrumtool.entities.Notification;
import devils.scrumtool.repositories.NotificationRepository;
import devils.scrumtool.services.NotificationService;
// Java libraries
import java.util.List;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// Notification API
@RestController
public class NotificationController {

    @Autowired private NotificationRepository notificationRepository;

    @Autowired private NotificationService notificationService;

    @GetMapping("/notifications")
    public Iterable<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @GetMapping("/users/{userId}/notifications")
    public List<Notification> getUserNotifications(@PathVariable Integer userId) {
        return notificationRepository.findByReceiver_Id(userId);
    }

    @PostMapping("/users/{userId}/notifications")
    public void insertNotification(
            @RequestBody Notification newNotification, @PathVariable Integer userId)
            throws Exception {
        notificationService.createNotification(newNotification, userId);
    }

    @DeleteMapping("/users/{userId}/notifications/{notificationId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteNotification(@PathVariable Integer notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
