package com.unisweets.unisweetsbackend.comment.service;

import com.unisweets.unisweetsbackend.comment.CommentDto;
import com.unisweets.unisweetsbackend.comment.model.Comment;
import com.unisweets.unisweetsbackend.comment.repository.CommentRepository;
import com.unisweets.unisweetsbackend.user.model.UserClient;
import com.unisweets.unisweetsbackend.user.service.UserClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserClientService userClientService;

    public Comment createComment(CommentDto commentDto) {
        UserClient sender = userClientService.getUserClientByUsername(commentDto.getSenderUsername());
        Comment comment = new Comment(sender, commentDto.getRating(), commentDto.getText());
        return commentRepository.save(comment);
    }


//    public List<Comment> findAllCommentsByReceiverUsername(String username){
//        UserPastry receiver = userPastryService.getUserPastryByUsername(username);
//        return commentRepository.findAllByReceiver(receiver);
//    }
}
