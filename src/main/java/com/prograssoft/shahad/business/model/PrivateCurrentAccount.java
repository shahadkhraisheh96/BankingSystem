package com.prograssoft.shahad.business.model;

import com.prograssoft.shahad.business.model.enums.AccountType;

public class PrivateCurrentAccount extends Account {
    protected PrivateCurrentAccount(Builder builder) {
        super(builder);
        this.setAccountType(AccountType.CURRENT);
    }

    public PrivateCurrentAccount() {
        super();
        this.setAccountType(AccountType.CURRENT);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Account.Builder {
        protected Builder() {
        }

        public PrivateCurrentAccount build() {

            return new PrivateCurrentAccount(this);
        }

    }
}
