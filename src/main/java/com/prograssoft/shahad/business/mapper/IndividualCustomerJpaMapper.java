package com.prograssoft.shahad.business.mapper;

import com.prograssoft.shahad.business.model.IndividualCustomer;
import com.prograssoft.shahad.business.storage.entity.IndividualCustomerEntity;

public class IndividualCustomerJpaMapper {
    public static IndividualCustomerEntity toIndividualCustomerEntity(IndividualCustomer individualCustomer) {
        IndividualCustomerEntity individualCustomerEntity = new IndividualCustomerEntity();
        individualCustomerEntity.setFirstName(individualCustomer.getFirstName());
        individualCustomerEntity.setLastName(individualCustomer.getLastName());
        individualCustomerEntity.setCustomerId(individualCustomer.getId());
        individualCustomerEntity.setBirthDate(individualCustomer.getBirthDate());
        individualCustomerEntity.setNationalId(individualCustomer.getNationalId());
        individualCustomerEntity.setCustomerId(individualCustomer.getId());
        individualCustomerEntity.setPhoneNumber(individualCustomer.getPhoneNumber());
        individualCustomerEntity.setRegitrationDate(individualCustomer.getRegistrationDate());
        return individualCustomerEntity;
    }

    public static IndividualCustomer toIndividualCustomer(IndividualCustomerEntity individualCustomerEntity) {
        return (IndividualCustomer) IndividualCustomer.builder()
                .firstName(individualCustomerEntity.getFirstName())
                .lastName(individualCustomerEntity.getLastName())
                .birthDate(individualCustomerEntity.getBirthDate())
                .nationalId(individualCustomerEntity.getNationalId())
                .id(individualCustomerEntity.getCustomerId())
                .phoneNumber(individualCustomerEntity.getPhoneNumber())
                .regitrationDate(individualCustomerEntity.getRegitrationDate())
                .build();
    }

}
