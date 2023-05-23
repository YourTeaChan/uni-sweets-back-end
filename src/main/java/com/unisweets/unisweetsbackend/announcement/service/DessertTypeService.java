package com.unisweets.unisweetsbackend.announcement.service;

import com.unisweets.unisweetsbackend.announcement.model.DessertType;
import com.unisweets.unisweetsbackend.announcement.repository.DessertTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DessertTypeService {
    private final DessertTypeRepository dessertTypeRepository;

    public List<DessertType> getAllDessertTypes(){
        return dessertTypeRepository.findAll();
    }
}
