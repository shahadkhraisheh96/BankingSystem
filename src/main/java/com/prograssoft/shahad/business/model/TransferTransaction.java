package com.prograssoft.shahad.business.model;

import jakarta.validation.constraints.NotBlank;

public class TransferTransaction extends Transaction {
    @NotBlank
    private String accountNumberTo;

    protected TransferTransaction(Builder builder) {
        super(builder);
        this.accountNumberTo = builder.accountNumberTo;
    }

    public TransferTransaction() {
    }

    public String getAccountNumberTo() {
        return accountNumberTo;
    }

    public void setAccountNumberTo(String accountNumberTo) {
        this.accountNumberTo = accountNumberTo;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Transaction.Builder<Builder> {
        private String accountNumberTo;

        @Override
        protected Builder self() {
            return this;
        }

        public Builder accountNumberTo(String accountNumberTo) {
            this.accountNumberTo = accountNumberTo;
            return this;
        }


        @Override
        public TransferTransaction build() {
            if (accountNumber == null || accountNumberTo == null || amount == null) {
                throw new IllegalStateException("Account numbers and amount must be provided");
            }
            return new TransferTransaction(this);
        }
    }

}
