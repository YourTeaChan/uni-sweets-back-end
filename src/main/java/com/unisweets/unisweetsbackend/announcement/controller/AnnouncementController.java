package com.unisweets.unisweetsbackend.announcement.controller;

import com.unisweets.unisweetsbackend.announcement.dto.AnnouncementDto;
import com.unisweets.unisweetsbackend.announcement.filter.AnnouncementClientFilter;
import com.unisweets.unisweetsbackend.announcement.filter.AnnouncementFilterDto;
import com.unisweets.unisweetsbackend.announcement.filter.TaskFilter;
import com.unisweets.unisweetsbackend.announcement.model.Announcement;
import com.unisweets.unisweetsbackend.announcement.service.AnnouncementService;
import com.unisweets.unisweetsbackend.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/announcements")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @GetMapping("/{username}/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable String username, @PathVariable Long id) {
        return ResponseEntity.ok(announcementService.getAnnouncementById(username, id));
    }

    @GetMapping
    public ResponseEntity<List<Announcement>> getAllAnnouncement() {
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }

    @PostMapping("/{username}")
    public ResponseEntity<List<Announcement>> getAllAnnouncement(@PathVariable String username,
                                                                 @RequestBody AnnouncementFilterDto announcementFilterDto) {
        return ResponseEntity.ok(announcementService.getAllAnnouncements(announcementFilterDto, username));
    }

    @PostMapping("/tasks/{username}")
    public ResponseEntity<List<Announcement>> getAllTasksByFilter(@PathVariable String username, @RequestBody TaskFilter taskFilter) {
        return ResponseEntity.ok(announcementService.getAllTasksByFilter(taskFilter, username));
    }

    @PostMapping
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody AnnouncementDto announcementDto) {
        return ResponseEntity.ok(announcementService.createAnnouncement(announcementDto));
    }

    @PostMapping("/{id}/offers/{username}/{price}")
    public ResponseEntity<Announcement> createOffer(@PathVariable Long id, @PathVariable String username, @PathVariable Integer price) {
        return ResponseEntity.ok(announcementService.createOffer(id, username, price));
    }

    @DeleteMapping("/{id}/offers/{offerId}")
    public ResponseEntity<Announcement> deleteOffer(@PathVariable Long id, @PathVariable Long offerId) {
        return ResponseEntity.ok(announcementService.deleteOffer(id, offerId));
    }

    @PutMapping("/{id}/final-pastry/{offerId}")
    public ResponseEntity<Announcement> chooseOffer(@PathVariable Long id, @PathVariable Long offerId) {
        return ResponseEntity.ok(announcementService.chooseOffer(id, offerId));
    }

    @PutMapping("/{id}/payment")
    public ResponseEntity<Announcement> payForAnnouncement(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.payForAnnouncement(id));
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<Announcement> finishAnnouncement(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.finishAnnouncement(id));
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<Announcement> closeAnnouncement(@RequestBody CommentDto commentDto,
                                                          @PathVariable Long id) {
        return ResponseEntity.ok(announcementService.closeAnnouncement(commentDto, id));
    }

    @PostMapping("/clients/{username}")
    public ResponseEntity<List<Announcement>> getAllClientAnnouncements(@PathVariable String username, @RequestBody AnnouncementClientFilter announcementClientFilter) {
        return ResponseEntity.ok(announcementService.getAllClientAnnouncements(username, announcementClientFilter));
    }

    @GetMapping("/clients/closed/{clientUsername}")
    public ResponseEntity<Integer> getUserClientClosedAnnouncementCount(@PathVariable String clientUsername) {
        return ResponseEntity.ok(announcementService.getUserClientClosedAnnouncementCount(clientUsername));
    }

    @GetMapping("/pastries/closed/{pastryUsername}")
    public ResponseEntity<Integer> getUserPastryClosedAnnouncementCount(@PathVariable String pastryUsername) {
        return ResponseEntity.ok(announcementService.getUserPastryClosedAnnouncementCount(pastryUsername));
    }
}
