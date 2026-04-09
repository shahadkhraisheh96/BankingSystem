package com.prograssoft.shahad.business.mapper;

import com.prograssoft.shahad.business.model.DepositTransaction;
import com.prograssoft.shahad.business.model.Transaction;
import com.prograssoft.shahad.business.model.TransferTransaction;
import com.prograssoft.shahad.business.model.WithdrawalTransaction;
import com.prograssoft.shahad.business.storage.entity.TransactionEntity;

public class TransactionJpaMapper {
    public static TransactionEntity toTransactionEntity(Transaction transaction) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(transaction.getId());
        transactionEntity.setAccountNumber(transaction.getAccountNumber());
        transactionEntity.setAmount(transaction.getAmount());
        transactionEntity.setTransactionDate(transaction.getTransactionDate());
        transactionEntity.setType(transaction.getClass().getSimpleName());
        if (transaction instanceof TransferTransaction transferTransaction) {
            transactionEntity.setAccountNumberTo(transferTransaction.getAccountNumberTo());
        }

        return transactionEntity;
    }
    public static Transaction toTransaction(TransactionEntity transactionEntity) {
        String type = transactionEntity.getType();
        switch (type) {
            case "DepositTransaction":
                return DepositTransaction.builder()
                        .id(transactionEntity.getId())
                        .accountNumber(transactionEntity.getAccountNumber())
                        .type(DepositTransaction.class.getSimpleName())
                        .amount(transactionEntity.getAmount())
                        .transactionDate(transactionEntity.getTransactionDate())
                        .build();

            case "WithdrawalTransaction":
                return WithdrawalTransaction.builder()
                        .id(transactionEntity.getId())
                        .accountNumber(transactionEntity.getAccountNumber())
                        .type(transactionEntity.getType())
                        .amount(transactionEntity.getAmount())
                        .transactionDate(transactionEntity.getTransactionDate())
                        .build();

            case "TransferTransaction":
                return TransferTransaction.builder()
                        .accountNumber(transactionEntity.getAccountNumber())
                        .id(transactionEntity.getId())
                        .accountNumberTo(transactionEntity.getAccountNumber())
                        .type(transactionEntity.getType())
                        .amount(transactionEntity.getAmount())
                        .transactionDate(transactionEntity.getTransactionDate())
                        .build();

            default:
                throw new IllegalArgumentException("Unknown transaction type: " + type);
        }
}}
