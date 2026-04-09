package com.prograssoft.shahad.application.api;

import com.prograssoft.shahad.business.model.CorporateCustomer;
import com.prograssoft.shahad.business.model.Customer;
import com.prograssoft.shahad.business.model.IndividualCustomer;
import com.prograssoft.shahad.business.model.enums.CustomerType;
import com.prograssoft.shahad.business.service.interfaces.CustomerService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerApi {
    private final CustomerService customerService;
    public CustomerApi(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value="/individual")
    public IndividualCustomer addIndividual(@RequestBody IndividualCustomer customer) {
        customer.setCustomerType(CustomerType.INDIVIDUAL);
        return (IndividualCustomer) customerService.createCustomer(customer);
    }


    @PostMapping(value="/corporate")
    public CorporateCustomer addCorporate(@RequestBody CorporateCustomer customer) {
        customer.setCustomerType(CustomerType.CORPORATE);
        return (CorporateCustomer) customerService.createCustomer(customer);
    }
    @GetMapping("/{type}/id/{id}")
    public Customer getCustomerByTypeAndId(@PathVariable String type, @PathVariable String id) {
        return customerService.findCustomer(type, id).orElseThrow(()-> new RuntimeException("Customer not foud"));
    }
   @GetMapping("/{type}")
    public List<? extends Customer> getAllCustomers(@PathVariable String type) {
        return customerService.allCustomer(type);
    }
    @GetMapping("/{type}/name/{name}")
    public  List<? extends Customer> findCustomerByTypeAndName(@PathVariable String type,@PathVariable   String name){

        return customerService.findCustomerByTypeAndName(type, name);
    }


}
