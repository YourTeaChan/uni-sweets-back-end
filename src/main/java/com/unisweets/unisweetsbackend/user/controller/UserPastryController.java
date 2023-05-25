package com.unisweets.unisweetsbackend.user.controller;

import com.unisweets.unisweetsbackend.comment.CommentDto;
import com.unisweets.unisweetsbackend.comment.model.Comment;
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
}
