package com.prograssoft.shahad.business.storage.repository;

import com.prograssoft.shahad.business.model.CorporateCustomer;
import com.prograssoft.shahad.business.model.Customer;
import com.prograssoft.shahad.business.storage.repository.interfaces.CustomerRepository;

import java.util.*;


public class CorporateCustomerRepository implements CustomerRepository<CorporateCustomer> {
    private final HashMap<String, CorporateCustomer> saveCustomer = new HashMap<>();

    @Override
    public Customer addCustomer(CorporateCustomer customer) {
        saveCustomer.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public Optional<CorporateCustomer> findCustomer(String id) {
        return   Optional.ofNullable(saveCustomer.get(id));
    }

    @Override
    public List<CorporateCustomer> findAll() {
        return new ArrayList<>(saveCustomer.values());
    }

    @Override
    public List<CorporateCustomer> findByName(String Name) {
        List<CorporateCustomer> customerList = new ArrayList<>();
        String lowerName = Name.toLowerCase();
        for(CorporateCustomer customer:saveCustomer.values()) {
            CorporateCustomer CC = customer;
            String customerName = (CC.getName()).toLowerCase();
            if (customerName.contains(lowerName)) {
                customerList.add(CC);
            }
        }
        return customerList;
    }
}
