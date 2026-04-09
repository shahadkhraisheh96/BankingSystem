package com.prograssoft.shahad.business.model;

import com.prograssoft.shahad.business.model.enums.AccountType;
import com.prograssoft.shahad.business.model.enums.AcountState;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Account {
    @NotNull
    private String customerId;
    @NotNull
    private String accountNumber;
    @NotNull
    private BigDecimal accountBalance;
    @NotNull
    private AcountState accountState;
    @NotNull
    private LocalDate creationDate;
    @NotNull
    private AccountType accountType;

    protected Account(Builder builder) {
        this.customerId = builder.customerId;
        this.accountNumber = builder.accountNumber;
        this.accountBalance = builder.accountBalance;
        this.accountState = builder.acountState;
        this.creationDate = builder.creationDate;
        this.accountType = builder.accountType;
    }

    public Account() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public AcountState getAccountState() {
        return accountState;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountState(AcountState acountState) {
        this.accountState = acountState;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public static class Builder {
        private String customerId;
        private String accountNumber;
        private BigDecimal accountBalance;
        private AcountState acountState;
        private LocalDate creationDate;
        private AccountType accountType;


        protected Builder() {
        }

        public Builder accountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public Builder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder accountNumber(String acountnumber) {
            this.accountNumber = acountnumber;
            return this;

        }

        public Builder accountBalance(BigDecimal accountBalance) {
            this.accountBalance = accountBalance;
            return this;
        }

        public Builder accountState(AcountState acountState) {
            this.acountState = acountState;
            return this;
        }

        public Builder creationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Account build() {
            return new Account(this);
        }


    }

    @Override
    public String toString() {
        return "Account{" +
                "customerId='" + customerId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountBalance=" + accountBalance +
                ", accountState=" + accountState +
                ", creationDate=" + creationDate +
                ", accountType=" + getClass().getSimpleName() +
                '}';
    }
}
