package com.prograssoft.shahad.application.api;

import com.prograssoft.shahad.business.model.Account;
import com.prograssoft.shahad.business.model.CorporateAccount;
import com.prograssoft.shahad.business.model.PrivateCurrentAccount;
import com.prograssoft.shahad.business.model.PrivateSavingAccount;
import com.prograssoft.shahad.business.service.interfaces.AccountService;
import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@Validated
public class AccountApi {
    private final AccountService accountService;
    public AccountApi(AccountService accountService) {
        this.accountService = accountService;

    }

    @GetMapping("/customer/{customerId}")
    public List<Account> listAllAccountsByCustomer(@PathVariable String customerId) {
        return accountService.listAllAccountsByCustomer(customerId);
    }



    @GetMapping("/number/{accountNumber}")
    public Optional<Account> listAccount(@PathVariable String accountNumber){
        return accountService.listAccount(accountNumber);
    }

    @PostMapping("/add")
    public Account createAccount(@Valid @RequestBody Account account) {
        Account newAccount;
        switch (account.getAccountType()) {
            case CURRENT -> newAccount = PrivateCurrentAccount.builder()
                    .customerId(account.getCustomerId())
                    .accountNumber(account.getAccountNumber())
                    .accountBalance(account.getAccountBalance())
                    .accountState(account.getAccountState())
                    .creationDate(account.getCreationDate())
                    .build();
            case CORPORATE -> newAccount = CorporateAccount.builder()
                    .customerId(account.getCustomerId())
                    .accountNumber(account.getAccountNumber())
                    .accountBalance(account.getAccountBalance())
                    .accountState(account.getAccountState())
                    .creationDate(account.getCreationDate())
                    .build();
            case SAVING ->newAccount= PrivateSavingAccount.builder()
                    .customerId(account.getCustomerId())
                    .accountNumber(account.getAccountNumber())
                    .accountBalance(account.getAccountBalance())
                    .accountState(account.getAccountState())
                    .creationDate(account.getCreationDate())
                    .build();
            default -> throw new IllegalArgumentException("Unknown account type");
        }

        accountService.creatAccount(newAccount);
        return newAccount;
    }

}
