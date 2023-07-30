package com.unisweets.unisweetsbackend.picture;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pictureId;
    private String pictureName = UUID.randomUUID() + ".jpg";
    private Instant time = Instant.now();
    private String pictureURL = "http://192.168.0.106:8080/api/v1/content/" + pictureName;
}
