package com.prograssoft.shahad.business.mapper;

import com.prograssoft.shahad.business.model.CorporateCustomer;
import com.prograssoft.shahad.business.storage.entity.CorporateCustomerEntity;

public class CorporateCustomerJpaMapper {
   public static CorporateCustomerEntity toCorporateCustomerEntity(CorporateCustomer corporateCustomer) {
        CorporateCustomerEntity corporateCustomerEntity = new CorporateCustomerEntity();
        corporateCustomerEntity.setCustomerId(corporateCustomer.getId());
        corporateCustomerEntity.setName(corporateCustomer.getName());
        corporateCustomerEntity.setPhoneNumber(corporateCustomer.getPhoneNumber());
        corporateCustomerEntity.setRegitrationDate(corporateCustomer.getRegistrationDate());
        corporateCustomerEntity.setTradeLicenseNumber(corporateCustomer.getTradeLicenseNumber());
        corporateCustomerEntity.setFoundingDate(corporateCustomer.getFoundingDate());
        return corporateCustomerEntity;
    }

    public static CorporateCustomer toCorporateCustomer(CorporateCustomerEntity corporateCustomerEntity) {
        return CorporateCustomer.builder()
                .id(corporateCustomerEntity.getCustomerId())
                .phoneNumber(corporateCustomerEntity.getPhoneNumber())
                .regitrationDate(corporateCustomerEntity.getRegitrationDate())
                .name(corporateCustomerEntity.getName())
                .tradeLicenseNumber(corporateCustomerEntity.getTradeLicenseNumber())
                .foundingDate(corporateCustomerEntity.getFoundingDate())
                .build();
    }
}
