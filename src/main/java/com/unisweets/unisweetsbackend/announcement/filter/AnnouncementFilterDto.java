package com.unisweets.unisweetsbackend.announcement.filter;

import lombok.Data;

import java.util.List;

@Data
public class AnnouncementFilterDto {
    private List<String> dessertTypes;
    private List<String> locations;
    private String date;
    private Boolean isQuick;
    private Boolean fromFavorites;
    private String sortingOrder;
}
