package com.unisweets.unisweetsbackend.message.service;

import com.unisweets.unisweetsbackend.message.ChatDto;
import com.unisweets.unisweetsbackend.message.ChatMapper;
import com.unisweets.unisweetsbackend.message.MessageDto;
import com.unisweets.unisweetsbackend.message.MessageMapper;
import com.unisweets.unisweetsbackend.message.model.Chat;
import com.unisweets.unisweetsbackend.message.repository.ChatRepository;
import com.unisweets.unisweetsbackend.message.repository.MessageRepository;
import com.unisweets.unisweetsbackend.user.model.User;
import com.unisweets.unisweetsbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserService userService;
    private final MessageMapper messageMapper;
    private final ChatMapper chatMapper;

    public Chat createChatOrGetExisting(String username1, String username2) {
        User firstUser = userService.getUserByUsername(username1);
        User secondUser = userService.getUserByUsername(username2);

        return chatRepository
                .findChatByFirstUserAndSecondUserOrFirstUserAndSecondUser(firstUser, secondUser, secondUser, firstUser)
                .orElseGet(() -> chatRepository.save(new Chat(firstUser, secondUser)));
    }

    public List<ChatDto> sendMessage(String username1, String username2, MessageDto messageDto) {
        Chat chat = createChatOrGetExisting(username1, username2);
        chat.addMessage(messageRepository.save(messageMapper.mapToEntity(messageDto)));
        chatRepository.save(chat);
        return getAllChatsForUser(messageDto.getSenderUsername());
    }

    public List<ChatDto> getAllChatsForUser(String username) {
        User user = userService.getUserByUsername(username);
        return chatRepository
                .findAllByFirstUserOrSecondUser(user, user)
                .stream()
                .filter(chat -> !chat.getMessages().isEmpty())
                .sorted(Comparator.comparing(Chat::getTime).reversed())
                .map(chatMapper::mapToDto)
                .toList();
    }

    public List<MessageDto> getMessages(String username1, String username2) {
        Chat chat = createChatOrGetExisting(username1, username2);
        return chat.getMessages().stream().map(messageMapper::mapToDto).toList();
    }
}
