package com.prograssoft.shahad.business.storage.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="INDIVIDUAL_CUSTOMER")
@PrimaryKeyJoinColumn(name="CUSTOMER_ID")
public class IndividualCustomerEntity extends CustomerEntity{
    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private  String lastName;
    @Column(name = "NATIONAL_ID")
    private long nationalId;
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getNationalId() {
        return nationalId;
    }

    public void setNationalId(long natiinoalId) {
        this.nationalId = natiinoalId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
