package com.unisweets.unisweetsbackend.announcement.service;

import com.unisweets.unisweetsbackend.announcement.model.Offer;
import com.unisweets.unisweetsbackend.announcement.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;


    public Offer getOfferById(Long offerId){
        return offerRepository.findById(offerId).orElseThrow();
    }
}
