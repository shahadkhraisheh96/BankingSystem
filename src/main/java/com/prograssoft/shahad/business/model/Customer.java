package com.prograssoft.shahad.business.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.prograssoft.shahad.business.model.enums.CustomerType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonDeserialize(using = CustomerDeserializer.class)
public abstract class Customer {
    @NotBlank
    protected String id;
    @Min(1)
    protected long phoneNumber;
    @NotNull
    protected LocalDate registrationDate;
    @NotNull
    protected CustomerType customerType;
    protected Customer(Builder<?> builder) {
        this.id = builder.id;
        this.phoneNumber = builder.phoneNumber;
        this.registrationDate = builder.registrationDate;
        this.customerType=builder.customerType;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }
    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }


    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public abstract static class Builder<T extends Builder<T>> {
        protected String id;
        protected long phoneNumber;
        protected LocalDate registrationDate;
        protected CustomerType customerType;

        protected Builder() {
        }

        public T id(String id) {
            this.id = id;
            return self();
        }
        public T customerType(CustomerType customerType) {
            this.customerType = customerType;
            return self();
        }

        public T phoneNumber(long phoneNumber) {
            this.phoneNumber = phoneNumber;
            return self();
        }

        public T regitrationDate(LocalDate regitrationDate) {
            this.registrationDate = regitrationDate;
            return self();
        }

        protected abstract T self();

        public abstract Customer build();
    }

}
