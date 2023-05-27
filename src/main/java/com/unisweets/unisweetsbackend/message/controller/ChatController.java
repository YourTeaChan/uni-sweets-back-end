package com.unisweets.unisweetsbackend.message.controller;

import com.unisweets.unisweetsbackend.message.ChatMapper;
import com.unisweets.unisweetsbackend.message.MessageDto;
import com.unisweets.unisweetsbackend.message.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/chats")
public class ChatController {
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    @GetMapping("{username1}/{username2}")
    public ResponseEntity<?> getChat(@PathVariable String username1, @PathVariable String username2) {
        return ResponseEntity.ok(chatMapper.mapToDto(chatService.createChatOrGetExisting(username1, username2)));
    }

    @PostMapping("{username1}/{username2}")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto, @PathVariable String username1, @PathVariable String username2) {
        return ResponseEntity.ok(chatService.sendMessage(username1, username2, messageDto));
    }

//    @GetMapping("{username1}/{username2}")
//    public ResponseEntity<?> getMessages(@PathVariable String username1, @PathVariable String username2) {
//        return ResponseEntity.ok(chatService.getMessages(username1, username2));
//    }

    @GetMapping("{username}")
    public ResponseEntity<?> getChatForSpecificUser(@PathVariable String username) {
        return ResponseEntity.ok(chatService.getAllChatsForUser(username));
    }

}
