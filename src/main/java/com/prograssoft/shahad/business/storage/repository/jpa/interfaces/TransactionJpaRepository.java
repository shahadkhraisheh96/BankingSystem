package com.prograssoft.shahad.business.storage.repository.jpa.interfaces;

import com.prograssoft.shahad.business.model.Transaction;
import com.prograssoft.shahad.business.storage.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Integer> {

List<TransactionEntity> findByAccountNumber(String accountNumber);
}
