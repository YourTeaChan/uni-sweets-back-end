package com.unisweets.unisweetsbackend.announcement.controller;

import com.unisweets.unisweetsbackend.announcement.dto.LocationDto;
import com.unisweets.unisweetsbackend.announcement.model.Location;
import com.unisweets.unisweetsbackend.announcement.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public Location createLocation(@RequestBody LocationDto locationDto){
        return locationService.addLocation(locationDto);
    }

    @GetMapping
    public List<Location> getAllLocations(){
        return locationService.getAllLocations();
    }


}
