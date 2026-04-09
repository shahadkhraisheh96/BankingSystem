package com.prograssoft.shahad.business.model;


public class DepositTransaction extends Transaction {

    protected DepositTransaction(Builder builder) {
        super(builder);
    }

    public DepositTransaction() {
        super();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Transaction.Builder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public DepositTransaction build() {
            return new DepositTransaction(this);
        }
    }
}
