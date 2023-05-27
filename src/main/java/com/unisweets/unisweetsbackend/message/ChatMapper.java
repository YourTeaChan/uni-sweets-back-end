package com.unisweets.unisweetsbackend.message;

import com.unisweets.unisweetsbackend.message.model.Chat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class ChatMapper {
    private final MessageMapper messageMapper;

    public ChatDto mapToDto(Chat chat) {
        return new ChatDto(
                chat.getFirstUser(),
                chat.getSecondUser(),
                chat.getTime(),
                chat.getMessages()
                        .stream()
                        .map(messageMapper::mapToDto)
                        .toList()
        );
    }


}
