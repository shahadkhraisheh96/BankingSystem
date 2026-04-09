package com.prograssoft.shahad.business.storage.repository.jpa.interfaces;

import com.prograssoft.shahad.business.model.Account;
import com.prograssoft.shahad.business.model.IndividualCustomer;
import com.prograssoft.shahad.business.storage.entity.IndividualCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IndividualCustomerJpaRepository extends JpaRepository<IndividualCustomerEntity, String> {
   List<Account> findByCustomerId(String customerId);
    List<IndividualCustomerEntity> findByFirstName(String name);
}
