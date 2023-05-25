package com.unisweets.unisweetsbackend.comment;

import lombok.Data;

@Data
public class CommentDto {
    private String senderUsername;
    private Short rating;
    private String text;
}
