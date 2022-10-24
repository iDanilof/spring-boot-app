package com.example.springbootapp.web.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import com.example.springbootapp.web.model.CustomerDto;
import com.example.springbootapp.web.service.CustomerService;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public CustomerDto getCustomer(UUID customerId) {
        return CustomerDto.builder().id(UUID.randomUUID())
                .name("Kevincho")
                .build();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customer) {
        return CustomerDto.builder().id(UUID.randomUUID())
                .name("Alfonsillo")
                .build();
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDto customer) {
        //TODO
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        log.info("Deleting customer...");
    }
}
