package com.unisweets.unisweetsbackend.announcement.mapper;

import com.unisweets.unisweetsbackend.announcement.dto.AnnouncementDto;
import com.unisweets.unisweetsbackend.announcement.model.Announcement;
import com.unisweets.unisweetsbackend.announcement.service.DessertTypeService;
import com.unisweets.unisweetsbackend.announcement.service.LocationService;
import com.unisweets.unisweetsbackend.user.service.UserClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnnouncementMapper {
    private final UserClientService userClientService;
    private final LocationService locationService;
    private final DessertTypeService dessertTypeService;

    public Announcement mapToEntity(AnnouncementDto announcementDto) {
        return new Announcement(
                userClientService.getUserClientByUsername(announcementDto.getCreatorUsername()),
                announcementDto.getDeadline(),
                locationService.getLocationByName(announcementDto.getLocation()),
                dessertTypeService.getDessertTypeByName(announcementDto.getDessertType()),
                announcementDto.getIsQuick(),
                announcementDto.getNotifyFavorites(),
                announcementDto.getTitle(),
                announcementDto.getDescription(),
                announcementDto.getPictures()
        );
    }
}
