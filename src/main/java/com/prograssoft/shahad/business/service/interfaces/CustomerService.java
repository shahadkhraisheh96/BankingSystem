package com.prograssoft.shahad.business.service.interfaces;

import com.prograssoft.shahad.business.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    Optional<? extends Customer> findCustomer(String type, String id);
    List<? extends Customer> allCustomer(String type);

    List<? extends Customer> findCustomerByTypeAndName(String type, String name);


}