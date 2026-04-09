package com.prograssoft.shahad.business.storage.repository.sql;

import com.prograssoft.shahad.business.mapper.TransactionJpaMapper;
import com.prograssoft.shahad.business.model.Transaction;
import com.prograssoft.shahad.business.storage.entity.TransactionEntity;
import com.prograssoft.shahad.business.storage.repository.interfaces.TransactionRepository;
import com.prograssoft.shahad.business.storage.repository.jpa.interfaces.TransactionJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

public class SqlTransactionRepository implements TransactionRepository {
   private final TransactionJpaRepository transactionJpaRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public SqlTransactionRepository(TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    public List<TransactionEntity> findByAccountNumber(String accountNumber) {
        return entityManager.createQuery(
                        "SELECT t FROM TransactionEntity t WHERE t.accountNumber = :accountNumber", TransactionEntity.class)
                .setParameter("accountNumber", accountNumber)
                .getResultList();
    }

    @Override
    public void save(Transaction transaction) {
        this.transactionJpaRepository.save(TransactionJpaMapper.toTransactionEntity(transaction));
    }

    public TransactionEntity save(TransactionEntity transactionEntity){
        entityManager.persist(transactionEntity);
        return transactionEntity;
    }

    @Override
    public List<Transaction> listTransaction(String accountNumber) {
        return transactionJpaRepository.findByAccountNumber(accountNumber)
                .stream()
                .map(TransactionJpaMapper::toTransaction)
                .collect(Collectors.toList());    }
}
