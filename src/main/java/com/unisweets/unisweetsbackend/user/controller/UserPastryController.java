package com.unisweets.unisweetsbackend.user.controller;

import com.unisweets.unisweetsbackend.comment.CommentDto;
import com.unisweets.unisweetsbackend.comment.model.Comment;
import com.unisweets.unisweetsbackend.picture.Picture;
import com.unisweets.unisweetsbackend.user.UserPastryDto;
import com.unisweets.unisweetsbackend.user.model.UserClient;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import com.unisweets.unisweetsbackend.user.service.UserPastryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/pastries")
@RequiredArgsConstructor
public class UserPastryController {
    private final UserPastryService userPastryService;

    @PostMapping("/{username}/comments")
    public ResponseEntity<List<Comment>> addComment(@RequestBody CommentDto commentDto, @PathVariable String username) {
        return ResponseEntity.ok(userPastryService.addComment(commentDto, username));
    }

    @GetMapping("/{username}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsForPastry(@PathVariable String username) {
        return ResponseEntity.ok(userPastryService.getAllCommentsForPastry(username));
    }

    @PatchMapping("/{username}")
    public ResponseEntity<UserPastry> updateUserPastry(@RequestBody UserPastryDto userPastryDto, @PathVariable String username) {
        return ResponseEntity.ok(userPastryService.updateUserPastry(userPastryDto, username));
    }

    @PostMapping("{username}/pictures")
    public ResponseEntity<List<Picture>> addPicture(@RequestBody Picture picture, @PathVariable String username) {
        return ResponseEntity.ok(userPastryService.addPicture(picture, username));
    }

    @DeleteMapping("{username}/pictures/{id}")
    public ResponseEntity<List<Picture>> deletePicture(@PathVariable Long id, @PathVariable String username) {
        return ResponseEntity.ok(userPastryService.deletePicture(id, username));
    }

    @GetMapping("{username}/likedBy")
    public ResponseEntity<List<UserClient>> getLikedByForUserPastry(@PathVariable String username){
        return ResponseEntity.ok(userPastryService.getLikedByForUserPastry(username));
    }
}
