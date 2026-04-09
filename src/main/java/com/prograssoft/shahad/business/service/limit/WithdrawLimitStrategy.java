package com.prograssoft.shahad.business.service.limit;

import com.prograssoft.shahad.business.model.Account;

import java.math.BigDecimal;

public interface WithdrawLimitStrategy {

    public Boolean validate(Account account ,BigDecimal amount);

    Class<? extends Account> getAccountType();
}
