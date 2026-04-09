package com.prograssoft.shahad.business.storage.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="CUSTOMER")
public class CustomerEntity {
    @Id
    @Column(name="CUSTOMER_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerId;

    @Column(name="PHONE_NUMBER")
    private long phoneNumber;
    @Column(name="REGITRATION_DATE")
    private LocalDate regitrationDate;


    public String getCustomerId() {
        return customerId;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getRegitrationDate() {
        return regitrationDate;
    }

    public void setCustomerId(String id) {
        this.customerId = id;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRegitrationDate(LocalDate regitrationDate) {
        this.regitrationDate = regitrationDate;
    }
}
