package com.prograssoft.shahad.business.storage.repository;

import com.prograssoft.shahad.business.model.Transaction;
import com.prograssoft.shahad.business.storage.entity.TransactionEntity;
import com.prograssoft.shahad.business.storage.repository.interfaces.TransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.*;

public class TransactionRepositoryImpl implements TransactionRepository {
HashMap<String,List<Transaction>> saveTransaction=new HashMap<>();
    @PersistenceContext
    private EntityManager entityManager;

    private void saveForAccount(String accountNumber, Transaction transaction) {
        saveTransaction.computeIfAbsent(accountNumber,k-> Collections.synchronizedList(new ArrayList<>())).add(transaction);
    }


    @Override
    public List<TransactionEntity> findByAccountNumber(String accountNumber) {
        return entityManager.createQuery(
                        "SELECT t FROM TransactionEntity t WHERE t.accountNumber = :accountNumber", TransactionEntity.class)
                .setParameter("accountNumber", accountNumber)
                .getResultList();
    }

    @Override
    public void save(Transaction transaction) {
        if (transaction == null || transaction.getAccountNumber() == null) {
            throw new IllegalArgumentException("Transaction or account number cannot be null");
        }
        saveForAccount(transaction.getAccountNumber(), transaction);
    }

    @Override
    public List<Transaction> listTransaction(String accountNumber) {
        return new ArrayList<>(saveTransaction.getOrDefault(accountNumber, Collections.emptyList()));    }
}
