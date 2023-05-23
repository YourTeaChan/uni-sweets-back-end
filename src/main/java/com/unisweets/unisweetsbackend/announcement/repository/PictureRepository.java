package com.unisweets.unisweetsbackend.announcement.repository;

import com.unisweets.unisweetsbackend.announcement.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Long> {
}
