package com.prograssoft.shahad.business.storage.repository;

import com.prograssoft.shahad.business.model.Customer;
import com.prograssoft.shahad.business.model.IndividualCustomer;
import com.prograssoft.shahad.business.storage.repository.interfaces.CustomerRepository;

import java.util.*;

public class IndividualCustomerRepository implements CustomerRepository<IndividualCustomer> {
    private final HashMap<String, IndividualCustomer> saveCustomer = new HashMap<>();

    @Override
    public Customer addCustomer(IndividualCustomer customer) {
        saveCustomer.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public Optional<IndividualCustomer> findCustomer(String id) {
        return   Optional.ofNullable(saveCustomer.get(id));
    }

    @Override
    public List<IndividualCustomer> findAll() {
        return new ArrayList<IndividualCustomer>((Collection<? extends IndividualCustomer>) saveCustomer.values());
    }

    @Override
    public List<IndividualCustomer> findByName(String Name) {
        List<IndividualCustomer> customerList = new ArrayList<>();
        String lowerName = Name.toLowerCase();
        for (IndividualCustomer customer : saveCustomer.values()) {
                    IndividualCustomer IC = customer;
                    String customerName = (IC.getFirstName() + " " + IC.getLastName()).toLowerCase();
                    if (customerName.contains(lowerName)) {
                        customerList.add(IC);
                    }
            }

        return customerList;


    }

}
