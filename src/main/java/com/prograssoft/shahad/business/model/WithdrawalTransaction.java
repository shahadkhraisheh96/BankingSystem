package com.prograssoft.shahad.business.model;

public class WithdrawalTransaction extends Transaction {
    public WithdrawalTransaction() {
    }

    public WithdrawalTransaction(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Transaction.Builder<Builder> {
        protected Builder self() {
            return this;
        }

        public WithdrawalTransaction build() {
            return new WithdrawalTransaction(this );
        }
    }
}
