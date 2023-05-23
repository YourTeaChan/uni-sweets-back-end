package com.unisweets.unisweetsbackend.announcement.repository;

import com.unisweets.unisweetsbackend.announcement.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Boolean existsByLocationName(String name);

    Optional<Location> findByLocationName(String locationName);
}
