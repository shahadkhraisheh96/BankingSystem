package com.prograssoft.shahad.business.service;

import com.prograssoft.shahad.business.model.Account;
import com.prograssoft.shahad.business.storage.repository.interfaces.AccountRepository;
import com.prograssoft.shahad.business.service.interfaces.AccountService;

import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {

        this.accountRepository=accountRepository;
    }


    @Override
    public void creatAccount(Account account) {
        accountRepository.saveAccount(account);
        System.out.println("the account been created ");

    }

    @Override
    public List<Account> listAllAccountsByCustomer(String customerId) {
        return accountRepository.findAccountByCustomerId(customerId);
    }

    @Override
    public Optional<Account> listAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
}
