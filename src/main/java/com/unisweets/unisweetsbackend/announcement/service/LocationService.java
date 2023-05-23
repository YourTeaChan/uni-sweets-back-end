package com.unisweets.unisweetsbackend.announcement.service;

import com.unisweets.unisweetsbackend.announcement.dto.LocationDto;
import com.unisweets.unisweetsbackend.announcement.model.Location;
import com.unisweets.unisweetsbackend.announcement.repository.LocationRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location addLocation(LocationDto locationDto) {
        if (locationExistsByName(locationDto.getLocationName())) {
            throw new EntityExistsException("Location already exists!");
        }
        return locationRepository.save(new Location(locationDto.getLocationName()));
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Boolean locationExistsByName(String locationName) {
        return locationRepository.existsByLocationName(locationName);
    }

    public Location getLocationByName(String locationName) {
        return locationRepository.findByLocationName(locationName).orElseThrow(() -> new EntityNotFoundException("No location by this name"));
    }

}
