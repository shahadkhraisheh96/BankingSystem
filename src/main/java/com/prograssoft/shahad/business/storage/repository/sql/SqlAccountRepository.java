package com.prograssoft.shahad.business.storage.repository.sql;

import com.prograssoft.shahad.business.mapper.AccountJpaMapper;
import com.prograssoft.shahad.business.model.Account;
import com.prograssoft.shahad.business.storage.repository.interfaces.AccountRepository;
import com.prograssoft.shahad.business.storage.repository.jpa.interfaces.AccountJpaRepository;

import java.util.List;
import java.util.Optional;

public class SqlAccountRepository implements AccountRepository {
    private final AccountJpaRepository accountJpaRepository;

    public SqlAccountRepository(AccountJpaRepository accountJpaRepository) {
        this.accountJpaRepository = accountJpaRepository;
    }

    @Override
    public void saveAccount(Account account) {
        this.accountJpaRepository.save(AccountJpaMapper.toAccountEntity(account));

    }

    @Override
    public List<Account> findAccountById(String id) {
        return this.accountJpaRepository.findById(id).stream().map(AccountJpaMapper::toAccount).toList();
    }

    @Override
    public List<Account> findAccountByCustomerId(String id) {
        return this.accountJpaRepository.findAccountByCustomerId(id).stream().map(AccountJpaMapper::toAccount).toList();
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return this.accountJpaRepository.findByAccountNumber(accountNumber).map(AccountJpaMapper::toAccount);
    }

    @Override
    public List<Account> findAll() {
        return this.accountJpaRepository.findAll().stream().map(AccountJpaMapper::toAccount).toList();
    }

    @Override
    public List<Account> findByPhoneNumber(Long phoneNumber) {
        return this.accountJpaRepository.findByPhoneNumber(phoneNumber).stream().map(AccountJpaMapper::toAccount).toList();
    }
}
