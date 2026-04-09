package com.prograssoft.shahad.business.model;

import com.prograssoft.shahad.business.model.enums.AccountType;

public class PrivateSavingAccount extends Account {
    public PrivateSavingAccount(Account.Builder builder) {
        super(builder);
        this.setAccountType(AccountType.SAVING);
    }

    public PrivateSavingAccount() {
        super();
        this.setAccountType(AccountType.SAVING);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Account.Builder {
        public Builder() {
        }

        public PrivateSavingAccount build() {
            return new PrivateSavingAccount(this);
        }

    }
}