package com.prograssoft.shahad.business.storage.repository.interfaces;

import com.prograssoft.shahad.business.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    public void saveAccount(Account account);

    public List<Account> findAccountById(String id);
    public List<Account> findAccountByCustomerId(String id);

    public Optional<Account> findByAccountNumber(String accountNumber);

    public List<Account> findAll();
    public List<Account> findByPhoneNumber(Long phoneNumber);

}
