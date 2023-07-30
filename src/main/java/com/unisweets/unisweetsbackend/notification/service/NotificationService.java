package com.unisweets.unisweetsbackend.notification.service;

import com.unisweets.unisweetsbackend.notification.model.Notification;
import com.unisweets.unisweetsbackend.notification.repository.NotificationRepository;
import com.unisweets.unisweetsbackend.user.model.User;
import com.unisweets.unisweetsbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public List<Notification> getNotificationsForUser(String username) {
        User user = userService.getUserByUsername(username);
        return notificationRepository.findAllByTargetUserOrderByTimeDesc(user);
    }

    public Notification createNotification(String targetUsername, String senderUsername, String text) {
        User targetUser = userService.getUserByUsername(targetUsername);
        User senderUser = userService.getUserByUsername(senderUsername);
        return notificationRepository.save(new Notification(targetUser, senderUser, text));
    }

    public Notification createNotification(User targetUser, User senderUser, String text) {
        return notificationRepository.save(new Notification(targetUser, senderUser, text));
    }


    public List<Notification> setNotificationRead(String username, Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.setIsRead(true);
        notificationRepository.save(notification);
        return getNotificationsForUser(username);
    }
}
