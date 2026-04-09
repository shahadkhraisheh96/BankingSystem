package com.prograssoft.shahad.business.storage.repository.jpa.interfaces;

import com.prograssoft.shahad.business.model.CorporateCustomer;
import com.prograssoft.shahad.business.storage.entity.CorporateCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorporateCustomerJpaRepository extends JpaRepository<CorporateCustomerEntity, String> {
    List<CorporateCustomerEntity> findByName(String name);
}
