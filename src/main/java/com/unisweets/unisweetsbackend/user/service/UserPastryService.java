package com.unisweets.unisweetsbackend.user.service;

import com.unisweets.unisweetsbackend.authentication.security.Utils;
import com.unisweets.unisweetsbackend.comment.CommentDto;
import com.unisweets.unisweetsbackend.comment.model.Comment;
import com.unisweets.unisweetsbackend.comment.service.CommentService;
import com.unisweets.unisweetsbackend.picture.Picture;
import com.unisweets.unisweetsbackend.user.UserPastryDto;
import com.unisweets.unisweetsbackend.user.model.UserClient;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import com.unisweets.unisweetsbackend.user.repository.UserClientRepository;
import com.unisweets.unisweetsbackend.user.repository.UserPastryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPastryService {
    private final UserService userService;
    private final UserPastryRepository userPastryRepository;
    private final CommentService commentService;
    private final UserClientRepository userClientRepository;

    public UserPastry getUserPastryByUsername(String username) {
        return userPastryRepository.findUserPastriesByUsername(username).orElseThrow();
    }

    public List<Comment> addComment(CommentDto commentDto, String username) {
        UserPastry userPastry = getUserPastryByUsername(username);
        UserClient userClient = userClientRepository.findUserClientByUsername(commentDto.getSenderUsername()).orElseThrow();
        Assert.state(Utils.getCurrentUserEmail().equals(userClient.getEmail()), "Adding comment forbidden");
        Comment comment = commentService.createComment(commentDto);
        userPastry.addComment(comment);
        return userPastryRepository.save(userPastry).getComments();
    }

    public List<Comment> getAllCommentsForPastry(String username) {
        return getUserPastryByUsername(username).getComments();
    }

    public List<Picture> getAllPicturesForPastry(String username){
        return getUserPastryByUsername(username).getPictures();
    }

    public UserPastry updateUserPastry(UserPastryDto userPastryDto, String username){
        userService.updateUser(userPastryDto, username);
        UserPastry userPastry = getUserPastryByUsername(userPastryDto.getUsername());
        userPastry.setAbout(userPastryDto.getAbout());
        userPastry.setInstagram(userPastryDto.getInstagram());
        userPastry.setFacebook(userPastryDto.getFacebook());
        userPastry.setYoutube(userPastryDto.getYoutube());
        userPastry.setTiktok(userPastryDto.getTiktok());
        return userPastryRepository.save(userPastry);
    }


//    public List<Picture> addPicture(String username, )

}
