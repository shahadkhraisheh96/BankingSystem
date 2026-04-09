package com.prograssoft.shahad.business.storage.repository;

import com.prograssoft.shahad.business.model.Account;
import com.prograssoft.shahad.business.storage.repository.interfaces.AccountRepository;

import java.util.*;

public class AccountRepositoryImpl implements AccountRepository {
    HashMap<String, Account> saveAccount = new HashMap<>();

    @Override
    public void saveAccount(Account account) {
        saveAccount.put(account.getAccountNumber(), account);
    }

    @Override
    public List<Account> findAccountById(String id) {

        return Collections.singletonList(saveAccount.get(id));
    }

    @Override
    public List<Account> findAccountByCustomerId(String id) {
        return List.of();
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return Optional.ofNullable(saveAccount.get(accountNumber));
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(saveAccount.values());
    }

    @Override
    public List<Account> findByPhoneNumber(Long phoneNumber) {
        return List.of(saveAccount.get(phoneNumber));
    }
}
