package com.example.springbootapp.bootstrap;

import java.math.BigDecimal;

import com.example.springbootapp.domain.Beer;
import com.example.springbootapp.repository.BeerRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {

        if(beerRepository.count() == 0){
            beerRepository.save(Beer.builder()
                            .beerName("Beer bob")
                            .beerStyle("IPA")
                            .quantityToBrew(200)
                            .minOnHand(12)
                            .upc(4444L)
                            .price(new BigDecimal("11.80"))
                            .build());

            beerRepository.save(Beer.builder()
                    .beerName("Bar bar")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(44454L)
                    .price(new BigDecimal("10.80"))
                    .build());
        }

    }
}
