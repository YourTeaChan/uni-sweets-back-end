package com.unisweets.unisweetsbackend.announcement.repository;

import com.unisweets.unisweetsbackend.announcement.model.DessertType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DessertTypeRepository extends JpaRepository<DessertType,Long> {
    Optional<DessertType> findByDessertTypeName(String dessertTypeName);
}
