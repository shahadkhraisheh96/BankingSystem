package com.prograssoft.shahad.business.service;

import com.prograssoft.shahad.business.model.CorporateCustomer;
import com.prograssoft.shahad.business.model.Customer;
import com.prograssoft.shahad.business.model.IndividualCustomer;
import com.prograssoft.shahad.business.storage.repository.interfaces.CustomerRepository;
import com.prograssoft.shahad.business.service.interfaces.CustomerService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository<IndividualCustomer> individualCustomerRepositry;
    private final CustomerRepository<CorporateCustomer> corporateCustomerRepositry;


    public CustomerServiceImpl(CustomerRepository<IndividualCustomer> individualCustomerRepositry, CustomerRepository<CorporateCustomer> corporateCustomerRepositry) {
        this.individualCustomerRepositry = individualCustomerRepositry;
        this.corporateCustomerRepositry = corporateCustomerRepositry;
    }


    @Override
    public Customer createCustomer(Customer customer) {
        if (customer instanceof IndividualCustomer individualCustomer) {
           return individualCustomerRepositry.addCustomer(individualCustomer);
        } else if (customer instanceof CorporateCustomer corporateCustomer) {
            return corporateCustomerRepositry.addCustomer(corporateCustomer);
        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    public Optional<? extends Customer> findCustomer(String type, String id) {
       return getRepository(type).findCustomer(id);

    }

    @Override
    public List<? extends Customer> allCustomer(String type) {
      return getRepository(type).findAll();
    }

    @Override
    public List<? extends Customer> findCustomerByTypeAndName(String type, String name) {
        return getRepository(type).findByName(name);
    }

    private CustomerRepository<?> getRepository(String type) {
        if(type == null) {
            throw new NullPointerException("type is null");
        }
        return switch (type.toUpperCase()) {
            case "INDIVIDUAL" -> individualCustomerRepositry;
            case "CORPORATE" -> corporateCustomerRepositry;
            default ->
                throw new NoSuchElementException();
        };
    }
}
