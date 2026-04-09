package com.prograssoft.shahad.business.service.limit;

import com.prograssoft.shahad.business.exception.ValidationException;
import com.prograssoft.shahad.business.model.Account;
import com.prograssoft.shahad.business.model.PrivateCurrentAccount;

import java.math.BigDecimal;

public class PrivateCurrentLimitStrategy implements WithdrawLimitStrategy {

    private final BigDecimal maximumLimit = BigDecimal.valueOf(3000);

    @Override
    public Boolean validate(Account account ,BigDecimal amount) {
        return amount.compareTo(maximumLimit) <= 0;

    }

    @Override
    public Class<? extends Account> getAccountType() {
        return PrivateCurrentAccount.class;
    }

}
