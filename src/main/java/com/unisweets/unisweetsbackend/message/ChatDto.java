package com.unisweets.unisweetsbackend.message;

import com.unisweets.unisweetsbackend.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class ChatDto {
    private User firstUser;
    private User secondUser;
    private Instant time;
    private List<MessageDto> messages;
}
