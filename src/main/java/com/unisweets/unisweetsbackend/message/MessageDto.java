package com.unisweets.unisweetsbackend.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class MessageDto {
    private String senderUsername;
    private String receiverUsername;
    private String text;
    private Instant time;
}
