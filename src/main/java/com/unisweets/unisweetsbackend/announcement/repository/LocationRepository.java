package com.unisweets.unisweetsbackend.announcement.repository;

import com.unisweets.unisweetsbackend.announcement.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Boolean existsByLocationName(String name);

    Optional<Location> findByLocationName(String locationName);
}
