package com.prograssoft.shahad.business.storage.repository.interfaces;

import com.prograssoft.shahad.business.model.Transaction;
import com.prograssoft.shahad.business.storage.entity.TransactionEntity;

import java.util.List;

public interface TransactionRepository {
    List<TransactionEntity> findByAccountNumber(String accountNumber);
    void save(Transaction transaction);
    List<Transaction> listTransaction(String accountNumber);

}
