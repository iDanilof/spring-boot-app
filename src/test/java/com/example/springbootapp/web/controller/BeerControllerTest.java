package com.example.springbootapp.web.controller;

import java.util.UUID;

import com.example.springbootapp.web.model.BeerDto;
import com.example.springbootapp.web.model.BeerStyleEnum;
import com.example.springbootapp.web.service.BeerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    void getBeer() throws Exception {

        when(beerService.getBeerById(any())).thenReturn(BeerDto.builder().build());

        mockMvc.perform(get("/api/v1/beer/"  + UUID.randomUUID()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void createNewBeer() throws Exception {

        BeerDto beerDto = BeerDto.builder().id(UUID.randomUUID())
                .beerName("mickey")
                .price("12")
                .upc("1234")
                .beerStyle(BeerStyleEnum.IPA).build();
        String response = objectMapper.writeValueAsString(beerDto);

        when(beerService.saveNewBeer(beerDto)).thenReturn(beerDto);

        mockMvc.perform(post("/api/v1/beer").contentType(MediaType.APPLICATION_JSON).content(response)).andExpect(status().isCreated());


    }

    @Test
    void updateBeerById() throws Exception {

        BeerDto beerDto = BeerDto.builder().id(UUID.randomUUID())
                .beerName("mojito")
                .price("12")
                .upc("1234")
                .beerStyle(BeerStyleEnum.IPA).build();
        String response = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(response)).andExpect(status().isNoContent());
    }
}