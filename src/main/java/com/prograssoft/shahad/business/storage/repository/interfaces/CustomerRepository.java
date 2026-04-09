package com.prograssoft.shahad.business.storage.repository.interfaces;

import com.prograssoft.shahad.business.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository<TYPE extends  Customer> {
    Customer addCustomer(TYPE customer);

    Optional<TYPE> findCustomer(String id);

    List<TYPE> findAll();

    List<TYPE> findByName(String Name);

}
