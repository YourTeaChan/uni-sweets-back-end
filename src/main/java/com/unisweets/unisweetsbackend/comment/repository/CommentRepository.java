package com.unisweets.unisweetsbackend.comment.repository;

import com.unisweets.unisweetsbackend.comment.model.Comment;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
