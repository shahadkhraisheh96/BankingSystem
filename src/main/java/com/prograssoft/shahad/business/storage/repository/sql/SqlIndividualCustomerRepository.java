package com.prograssoft.shahad.business.storage.repository.sql;

import com.prograssoft.shahad.business.mapper.IndividualCustomerJpaMapper;
import com.prograssoft.shahad.business.model.Customer;
import com.prograssoft.shahad.business.model.IndividualCustomer;
import com.prograssoft.shahad.business.storage.entity.IndividualCustomerEntity;
import com.prograssoft.shahad.business.storage.repository.interfaces.CustomerRepository;
import com.prograssoft.shahad.business.storage.repository.jpa.interfaces.IndividualCustomerJpaRepository;

import java.util.List;
import java.util.Optional;

public class SqlIndividualCustomerRepository implements CustomerRepository<IndividualCustomer> {
    private final IndividualCustomerJpaRepository  individualCustomerJpaRepository;

    public SqlIndividualCustomerRepository(IndividualCustomerJpaRepository individualCustomerJpaRepository) {
        this.individualCustomerJpaRepository = individualCustomerJpaRepository;
    }

    @Override
    public Customer addCustomer(IndividualCustomer customer) {
        IndividualCustomerEntity entity = this.individualCustomerJpaRepository.save(IndividualCustomerJpaMapper.toIndividualCustomerEntity(customer));
        return IndividualCustomerJpaMapper.toIndividualCustomer(entity);
    }

    @Override
    public Optional<IndividualCustomer> findCustomer(String id) {
        return this.individualCustomerJpaRepository.findById(id).map(IndividualCustomerJpaMapper::toIndividualCustomer);
    }

    @Override
    public List<IndividualCustomer> findAll() {
        return this.individualCustomerJpaRepository.findAll().stream().map(IndividualCustomerJpaMapper::toIndividualCustomer).toList();
    }

    @Override
    public List<IndividualCustomer> findByName(String Name) {
        return this.individualCustomerJpaRepository.findByFirstName(Name).stream().map(IndividualCustomerJpaMapper::toIndividualCustomer).toList();
    }
}
