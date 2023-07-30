package com.unisweets.unisweetsbackend.notification.controller;

import com.unisweets.unisweetsbackend.notification.model.Notification;
import com.unisweets.unisweetsbackend.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{username}")
    public ResponseEntity<List<Notification>> getAllNotificationsForUser(@PathVariable String username) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(username));
    }

    @PatchMapping("/{username}/{id}")
    public ResponseEntity<List<Notification>> setNotificationRead(@PathVariable String username, @PathVariable Long id) {
        return ResponseEntity.ok(notificationService.setNotificationRead(username, id));
    }
}
