package com.prograssoft.shahad.business.mapper;

import com.prograssoft.shahad.business.model.Account;
import com.prograssoft.shahad.business.model.CorporateAccount;
import com.prograssoft.shahad.business.model.PrivateCurrentAccount;
import com.prograssoft.shahad.business.model.PrivateSavingAccount;
import com.prograssoft.shahad.business.storage.entity.AccountEntity;
import com.prograssoft.shahad.business.storage.entity.CorporateAccountEntity;
import com.prograssoft.shahad.business.storage.entity.PrivateCurrentAccountEntity;
import com.prograssoft.shahad.business.storage.entity.PrivateSavingAccountEntity;

public class AccountJpaMapper {
    public static AccountEntity toAccountEntity(Account account) {
        AccountEntity accountEntity = createNewEntity(account);
        accountEntity.setCustomerId(account.getCustomerId());
        accountEntity.setAccountNumber(account.getAccountNumber());
        accountEntity.setAccountBalance(account.getAccountBalance());
        accountEntity.setAcountState(account.getAccountState());
        accountEntity.setCreationDate(account.getCreationDate());
        return accountEntity;

    }
    public static Account toAccount(AccountEntity accountEntity) {
        Account account = createNewModel(accountEntity);
        account.setCustomerId(accountEntity.getCustomerId());
        account.setAccountNumber(accountEntity.getAccountNumber());
        account.setAccountBalance(accountEntity.getAccountBalance());
        account.setAccountState(accountEntity.getAcountState());
        account.setCreationDate(accountEntity.getCreationDate());
        return account;
    }

    private static AccountEntity createNewEntity(Account account) {
        if(account instanceof PrivateCurrentAccount)
            return new PrivateCurrentAccountEntity();
        if(account instanceof PrivateSavingAccount)
            return new PrivateSavingAccountEntity();
        if(account instanceof CorporateAccount)
            return new CorporateAccountEntity();
        throw new IllegalArgumentException("Unknown Account Type");
    }

    private static Account createNewModel(AccountEntity account) {
        if(account instanceof PrivateCurrentAccountEntity)
            return new PrivateCurrentAccount();
        if(account instanceof PrivateSavingAccountEntity)
            return new PrivateSavingAccount();
        if(account instanceof CorporateAccountEntity)
            return new CorporateAccount();
        throw new IllegalArgumentException("Unknown Account Type");
    }
}
