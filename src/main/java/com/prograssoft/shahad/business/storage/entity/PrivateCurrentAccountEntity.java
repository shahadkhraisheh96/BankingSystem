package com.prograssoft.shahad.business.storage.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PRIVATE_CURRENT")
public class PrivateCurrentAccountEntity extends AccountEntity {
}
