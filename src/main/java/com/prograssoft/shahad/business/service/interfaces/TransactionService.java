package com.prograssoft.shahad.business.service.interfaces;

import com.prograssoft.shahad.business.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void deposit(String accountNumber, BigDecimal amount);
    void withdraw(String accountNumber,BigDecimal amount);
    void transfer(String accountNumberFrom,String accountNumberToo,BigDecimal amount);
    List<Transaction> listTransactionForAccount(String accountNumber);


}
