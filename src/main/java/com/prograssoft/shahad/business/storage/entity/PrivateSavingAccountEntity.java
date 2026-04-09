package com.prograssoft.shahad.business.storage.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PRIVATE_SAVING")
public class PrivateSavingAccountEntity extends AccountEntity {
}
