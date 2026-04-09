package com.prograssoft.shahad.business.storage.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "CORPORATE")
public class CorporateAccountEntity extends AccountEntity {
}
