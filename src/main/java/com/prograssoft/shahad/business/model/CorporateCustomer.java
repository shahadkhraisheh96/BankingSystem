package com.prograssoft.shahad.business.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prograssoft.shahad.business.model.enums.CustomerType;
import jakarta.persistence.Inheritance;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public class CorporateCustomer extends Customer {
    @NotBlank
    private String name;
    @NotBlank
    private long tradeLicenseNumber;
    @NotBlank
    private LocalDate foundingDate;

    public CorporateCustomer() {
        super(builder().id(UUID.randomUUID().toString())
                .customerType(CustomerType.CORPORATE)
                .regitrationDate(LocalDate.now()));
    }
    @JsonCreator
    public CorporateCustomer(@JsonProperty("name")String name,
                             @JsonProperty("tradeLicenseNumber") long tradeLicenseNumber,
                             @JsonProperty("foundingDate") LocalDate foundingDate) {
        super(builder().customerType(CustomerType.CORPORATE)
                .regitrationDate(LocalDate.now()));
        this.name = name;
        this.tradeLicenseNumber = tradeLicenseNumber;
        this.foundingDate = foundingDate;

    }

    public CorporateCustomer(Builder builder) {
        super(builder.customerType(CustomerType.CORPORATE));
        this.name = builder.name;
        this.tradeLicenseNumber = builder.tradeLicenseNumber;
        this.foundingDate = builder.foundingDate;
    }

    public String getName() {
        return name;
    }

    public long getTradeLicenseNumber() {
        return tradeLicenseNumber;
    }

    public LocalDate getFoundingDate() {
        return foundingDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTradeLicenseNumber(long tradeLicenseNumber) {
        this.tradeLicenseNumber = tradeLicenseNumber;
    }

    public void setFoundingDate(LocalDate foundingDate) {
        this.foundingDate = foundingDate;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder extends Customer.Builder<Builder> {
        private String name;
        private long tradeLicenseNumber;
        private LocalDate foundingDate;

        protected Builder() {}
        @Override
        protected Builder self() {
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder tradeLicenseNumber(long tradeLicenseNumber) {
            this.tradeLicenseNumber = tradeLicenseNumber;
            return this;
        }

        public Builder foundingDate(LocalDate foundingDate) {
            this.foundingDate = foundingDate;
            return this;
        }


        @Override
        public CorporateCustomer build() {
            return new CorporateCustomer(this);
        }
    }

}
