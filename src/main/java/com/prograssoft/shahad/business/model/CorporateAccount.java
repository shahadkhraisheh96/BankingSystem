package com.prograssoft.shahad.business.model;

import com.prograssoft.shahad.business.model.enums.AccountType;

public class CorporateAccount extends Account{
    public CorporateAccount(Builder builder) {
        super(builder);
        this.setAccountType(AccountType.CORPORATE);
    }

    public CorporateAccount() {
        super();
        this.setAccountType(AccountType.CORPORATE);
    }

    public static Builder builder(){
        return new Builder();
  }

    public static class Builder extends Account.Builder{
        protected Builder() {
        }
        public CorporateAccount build(){
            return  new CorporateAccount(this);
        }
    }
}
