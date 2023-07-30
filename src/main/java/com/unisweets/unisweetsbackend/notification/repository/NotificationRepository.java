package com.unisweets.unisweetsbackend.notification.repository;

import com.unisweets.unisweetsbackend.notification.model.Notification;
import com.unisweets.unisweetsbackend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findAllByTargetUserOrderByTimeDesc(User targetUser);
}
