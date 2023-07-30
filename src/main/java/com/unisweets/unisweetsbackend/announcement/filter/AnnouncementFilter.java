package com.unisweets.unisweetsbackend.announcement.filter;

import com.unisweets.unisweetsbackend.announcement.model.DessertType;
import com.unisweets.unisweetsbackend.announcement.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AnnouncementFilter {
    private List<DessertType> dessertTypes;
    private List<Location> locations;
    private String date;
    private Boolean isQuick;
    private Boolean fromFavorites;
    private String sortingOrder;
}
