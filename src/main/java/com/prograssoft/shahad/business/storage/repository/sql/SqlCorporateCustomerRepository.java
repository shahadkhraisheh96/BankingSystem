package com.prograssoft.shahad.business.storage.repository.sql;

import com.prograssoft.shahad.business.mapper.CorporateCustomerJpaMapper;
import com.prograssoft.shahad.business.model.CorporateCustomer;
import com.prograssoft.shahad.business.model.Customer;
import com.prograssoft.shahad.business.storage.entity.CorporateCustomerEntity;
import com.prograssoft.shahad.business.storage.repository.interfaces.CustomerRepository;
import com.prograssoft.shahad.business.storage.repository.jpa.interfaces.CorporateCustomerJpaRepository;

import java.util.List;
import java.util.Optional;

public class SqlCorporateCustomerRepository implements CustomerRepository<CorporateCustomer> {
    private final CorporateCustomerJpaRepository corporateCustomerJpaRepository;

    public SqlCorporateCustomerRepository(CorporateCustomerJpaRepository corporateCustomerJpaRepository) {
        this.corporateCustomerJpaRepository = corporateCustomerJpaRepository;
    }

    @Override
    public Customer addCustomer(CorporateCustomer customer) {
        CorporateCustomerEntity entity = corporateCustomerJpaRepository.save(CorporateCustomerJpaMapper.toCorporateCustomerEntity(customer));
        return CorporateCustomerJpaMapper.toCorporateCustomer(entity);

    }

    @Override
    public Optional<CorporateCustomer> findCustomer(String id) {
        return this.corporateCustomerJpaRepository.findById(id).map(CorporateCustomerJpaMapper::toCorporateCustomer);
    }

    @Override
    public List<CorporateCustomer> findAll() {
        return this.corporateCustomerJpaRepository.findAll().stream().map(CorporateCustomerJpaMapper::toCorporateCustomer).toList();
    }

    @Override
    public List<CorporateCustomer> findByName(String Name) {
        return this.corporateCustomerJpaRepository.findByName(Name).stream().map(CorporateCustomerJpaMapper::toCorporateCustomer).toList();
    }
}
