package com.example.springbootapp.web.service;

import java.util.UUID;

import com.example.springbootapp.web.model.CustomerDto;

import org.springframework.stereotype.Service;

public interface CustomerService {

    CustomerDto getCustomer(UUID customerId);

    CustomerDto createCustomer(CustomerDto customer);

    void updateCustomerById(UUID customerId, CustomerDto customer);

    void deleteCustomerById(UUID customerId);
}
