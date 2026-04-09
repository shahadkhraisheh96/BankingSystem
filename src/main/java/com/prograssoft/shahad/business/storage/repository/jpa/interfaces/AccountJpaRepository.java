package com.prograssoft.shahad.business.storage.repository.jpa.interfaces;

import com.prograssoft.shahad.business.model.Account;
import com.prograssoft.shahad.business.storage.entity.AccountEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {
    Optional<AccountEntity> findByAccountNumber(String accountNumber);
    List<AccountEntity> findAccountByCustomerId(String id);
    @Query("SELECT acc from AccountEntity acc join CustomerEntity cust on (acc.customerId = cust.customerId) where cust.phoneNumber = :pNumber")
    List<AccountEntity> findByPhoneNumber(@Param("pNumber") long phoneNumber);
}
