package com.prograssoft.shahad.business.model;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private String id;
    private String accountNumber;
    private String type;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public Transaction() {
    }

    protected Transaction(Builder<?> builder) {
        this.id = builder.id;
        this.accountNumber = builder.accountNumber;
        this.type = builder.type;
        this.amount = builder.amount;
        this.transactionDate = builder.transactionDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public static Builder<?> builder() {

        return new Builder<>();
    }

    public static class Builder<T extends Builder<T>> {
        protected String id;
        protected String accountNumber;
        protected String type;
        protected BigDecimal amount;
        protected LocalDateTime transactionDate;

        public T id(String id) {
            this.id = id;

            return self();
        }

        public T accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return self();
        }

        public T type(String type) {
            this.type = type;
            return self();
        }

        public T amount(BigDecimal amount) {
            this.amount = amount;
            return self();
        }

        public T transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return self();
        }

        protected T self() {
            return (T) this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
