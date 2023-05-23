package com.unisweets.unisweetsbackend.announcement.repository;

import com.unisweets.unisweetsbackend.announcement.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
