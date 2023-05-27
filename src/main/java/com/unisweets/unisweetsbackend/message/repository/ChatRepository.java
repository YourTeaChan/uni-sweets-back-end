package com.unisweets.unisweetsbackend.message.repository;

import com.unisweets.unisweetsbackend.message.model.Chat;
import com.unisweets.unisweetsbackend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByFirstUserOrSecondUser(User firstUser, User secondUser);

    Optional<Chat> findChatByFirstUserAndSecondUserOrFirstUserAndSecondUser(User firstUser, User secondUser, User firstUser2, User secondUser2);

}
