package com.example.springbootapp.web.service;

import java.util.UUID;

import com.example.springbootapp.web.model.BeerDto;

public interface BeerService {

    BeerDto getBeerById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beer);

    void updateBeer(UUID beerId, BeerDto beer);

    void deleteBeerById(UUID beerId);
}
