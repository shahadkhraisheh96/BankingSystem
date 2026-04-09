package com.prograssoft.shahad.business.storage.entity;

import com.prograssoft.shahad.business.model.enums.AccountType;
import com.prograssoft.shahad.business.model.enums.AcountState;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="ACCOUNT")
@DiscriminatorColumn(name = "ACCOUNT_TYPE")
public class AccountEntity {
    @Column(name="CUSTOMER_ID")
    private String customerId;
    @Id
    @Column(name="ACCOUNT_NUMBER")
    private String accountNumber;
    @Column(name="ACCOUNT_BALANCE")
    private BigDecimal accountBalance;
    @Column(name="ACCOUNT_STATE")
    private AcountState acountState;
    @Column(name="CREATION_DATE")
    private LocalDate creationDate;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public AcountState getAcountState() {
        return acountState;
    }

    public void setAcountState(AcountState acountState) {
        this.acountState = acountState;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }


}
