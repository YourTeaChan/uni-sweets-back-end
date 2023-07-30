package com.unisweets.unisweetsbackend.announcement.controller;

import com.unisweets.unisweetsbackend.announcement.dto.DessertTypeDto;
import com.unisweets.unisweetsbackend.announcement.model.DessertType;
import com.unisweets.unisweetsbackend.announcement.service.DessertTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/dessert-types")
@RequiredArgsConstructor
public class DessertTypeController {
    private final DessertTypeService dessertTypeService;

    @GetMapping
    public ResponseEntity<List<DessertType>> getAllDessertTypes(){
        return ResponseEntity.ok(dessertTypeService.getAllDessertTypes());
    }

    @PostMapping
    public ResponseEntity<DessertType> createDessertType(@RequestBody DessertTypeDto dessertTypeDto){
        return ResponseEntity.ok(dessertTypeService.createDessertType(dessertTypeDto));
    }
}
