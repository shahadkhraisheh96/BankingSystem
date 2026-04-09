package com.prograssoft.shahad.business.service.interfaces;

import com.prograssoft.shahad.business.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    void creatAccount(Account account);
    List<Account> listAllAccountsByCustomer(String customerId);
    Optional<Account> listAccount(String accountNumber);


}
