package com.unisweets.unisweetsbackend.message.repository;

import com.unisweets.unisweetsbackend.message.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
