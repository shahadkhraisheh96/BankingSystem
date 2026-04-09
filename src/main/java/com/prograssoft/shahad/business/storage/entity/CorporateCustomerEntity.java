package com.prograssoft.shahad.business.storage.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "CORPORATE_CUSTOMER")
@PrimaryKeyJoinColumn(name="CUSTOMER_ID")
public class CorporateCustomerEntity extends CustomerEntity {
    @Column(name="NAME")
    private String name;
    @Column(name = "TRADE_LICENSE_NUMBER")
    private long tradeLicenseNumber;
    @Column (name ="FOUNDING_DATE")
    private LocalDate foundingDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTradeLicenseNumber() {
        return tradeLicenseNumber;
    }

    public void setTradeLicenseNumber(long tradeLicenseNumber) {
        this.tradeLicenseNumber = tradeLicenseNumber;
    }

    public LocalDate getFoundingDate() {
        return foundingDate;
    }

    public void setFoundingDate(LocalDate foundingDate) {
        this.foundingDate = foundingDate;
    }
}
