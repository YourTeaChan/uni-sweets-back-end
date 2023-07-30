package com.unisweets.unisweetsbackend.announcement.dto;

import com.unisweets.unisweetsbackend.picture.Picture;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AnnouncementDto {
    private String creatorUsername;
    private LocalDate deadline;
    private String location;
    private String dessertType;
    private Boolean isQuick;
    private Boolean notifyFavorites;
    private String title;
    private String description;
    private List<Picture> pictures;
}
