package com.prograssoft.shahad.application.api;

import com.prograssoft.shahad.business.model.DepositTransaction;
import com.prograssoft.shahad.business.model.Transaction;
import com.prograssoft.shahad.business.model.TransferTransaction;
import com.prograssoft.shahad.business.model.WithdrawalTransaction;
import com.prograssoft.shahad.business.service.interfaces.TransactionService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Validated
public class TransactionApi {
    private final TransactionService transactionService;
    public TransactionApi(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping("/deposit")
    public void deposit(@Valid @RequestBody DepositTransaction depositTransaction) {
        transactionService.deposit(depositTransaction.getAccountNumber(), depositTransaction.getAmount());
    }
    @PostMapping(value = "/withdraw")
    public void withdraw(@Valid @RequestBody WithdrawalTransaction withdrawalTransaction) {
        transactionService.withdraw(withdrawalTransaction.getAccountNumber(), withdrawalTransaction.getAmount());
    }
    @PostMapping(value = "/transfer")
    public void transfer(@Valid @RequestBody TransferTransaction transferTransaction) {
        transactionService.transfer(transferTransaction.getAccountNumber(), transferTransaction.getAccountNumberTo(), transferTransaction.getAmount());
    }
    @GetMapping(value = "number/{accountNumber}")
    List<Transaction> listTransactionForAccount(@PathVariable String accountNumber){
        return transactionService.listTransactionForAccount(accountNumber);
    }
}
