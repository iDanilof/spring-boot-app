package com.example.springbootapp.web.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import com.example.springbootapp.web.model.BeerDto;
import com.example.springbootapp.web.model.BeerStyleEnum;
import com.example.springbootapp.web.service.BeerService;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyleEnum.ALE)
                .price("12")
                .upc("1234")
                .build();
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beer) {

        beer.setId(UUID.randomUUID());
        return beer;
    }

    @Override public void updateBeer(UUID beerId, BeerDto beer) {
        //TODO
    }

    @Override public void deleteBeerById(UUID beerId) {
        log.info("deleting a beer...");
    }
}
