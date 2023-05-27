package com.unisweets.unisweetsbackend.message;

import com.unisweets.unisweetsbackend.message.model.Message;
import com.unisweets.unisweetsbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {
    private final UserService userService;


    public MessageDto mapToDto(Message message) {
        return new MessageDto(
                message.getSender().getUsername(),
                message.getReceiver().getUsername(),
                message.getText(),
                message.getTime()
        );
    }

    public Message mapToEntity(MessageDto messageDto) {
        return new Message(
                userService.getUserByUsername(messageDto.getSenderUsername()),
                userService.getUserByUsername(messageDto.getReceiverUsername()),
                messageDto.getText()
        );
    }
}
