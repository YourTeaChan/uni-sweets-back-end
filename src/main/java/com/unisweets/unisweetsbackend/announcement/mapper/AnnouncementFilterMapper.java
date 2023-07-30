package com.unisweets.unisweetsbackend.announcement.mapper;

import com.unisweets.unisweetsbackend.announcement.filter.AnnouncementFilter;
import com.unisweets.unisweetsbackend.announcement.filter.AnnouncementFilterDto;
import com.unisweets.unisweetsbackend.announcement.service.DessertTypeService;
import com.unisweets.unisweetsbackend.announcement.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnnouncementFilterMapper {
    private final LocationService locationService;
    private final DessertTypeService dessertTypeService;

    public AnnouncementFilter mapToEntity(AnnouncementFilterDto announcementFilterDto) {
        return new AnnouncementFilter(
                announcementFilterDto.getDessertTypes().stream().map(dessertTypeService::getDessertTypeByName).toList(),
                announcementFilterDto.getLocations().stream().map(locationService::getLocationByName).toList(),
                announcementFilterDto.getDate(),
                announcementFilterDto.getIsQuick(),
                announcementFilterDto.getFromFavorites(),
                announcementFilterDto.getSortingOrder()
        );
    }
}
