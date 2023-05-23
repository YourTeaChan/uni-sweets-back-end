package com.unisweets.unisweetsbackend.announcement.repository;

import com.unisweets.unisweetsbackend.announcement.model.DessertType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DessertTypeRepository extends JpaRepository<DessertType,Long> {
}
