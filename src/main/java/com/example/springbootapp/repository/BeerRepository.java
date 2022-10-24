package com.example.springbootapp.repository;

import java.util.UUID;

import com.example.springbootapp.domain.Beer;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
}
