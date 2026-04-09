package com.prograssoft.shahad.application.runner;

import com.prograssoft.shahad.business.model.*;
import com.prograssoft.shahad.business.model.enums.AccountType;
import com.prograssoft.shahad.business.model.enums.AcountState;
import com.prograssoft.shahad.business.model.enums.CustomerType;
import com.prograssoft.shahad.business.service.interfaces.AccountService;
import com.prograssoft.shahad.business.service.interfaces.CustomerService;
import com.prograssoft.shahad.business.service.interfaces.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ApplicationRunner implements CommandLineRunner {
    private final CustomerService customerService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public ApplicationRunner(CustomerService customerService,
                             AccountService accountService,
                             TransactionService transactionService) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public void run(String... args) throws Exception {

        // Create first individual customer
        IndividualCustomer customer1 = IndividualCustomer.builder()
                .firstName("Shahad")
                .lastName("Khraisheh")
                .birthDate(LocalDate.of(2004, 9, 6))
                .nationalId(200929278L)
                .regitrationDate(LocalDate.now())
                .phoneNumber(9864773L)
                .build();

        customer1 = (IndividualCustomer) customerService.createCustomer(customer1);

        System.out.println(customerService.allCustomer(String.valueOf(CustomerType.INDIVIDUAL)));

        // Create a corporate account for customer1
        accountService.creatAccount(CorporateAccount.builder()
                .customerId(customer1.getId())
                .accountNumber("100029344")
                .accountBalance(BigDecimal.valueOf(2000))
                .accountType(AccountType.CORPORATE)
                .accountState(AcountState.OPEN)
                .creationDate(LocalDate.now())
                .build()
        );

        // Create second individual customer
        IndividualCustomer customer2 = IndividualCustomer.builder()
                .firstName("Shahad")
                .lastName("Khraisheh")
                .birthDate(LocalDate.of(2004, 9, 6))
                .nationalId(200929278)
                .regitrationDate(LocalDate.now())
                .phoneNumber(9864773)
                .build();

        customer2 = (IndividualCustomer) customerService.createCustomer(customer2);

        // Create third individual customer with minimal info
        IndividualCustomer customer3 = IndividualCustomer.builder()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .nationalId(123456789)
                .regitrationDate(LocalDate.now())
                .phoneNumber(1234567)
                .build();

        customer3 = (IndividualCustomer) customerService.createCustomer(customer3);

        System.out.println(customerService.allCustomer(String.valueOf(CustomerType.INDIVIDUAL)));

        // Create private current accounts
        accountService.creatAccount(PrivateCurrentAccount.builder()
                .customerId(customer2.getId())
                .accountNumber("00001")
                .accountBalance(BigDecimal.valueOf(3000))
                .accountState(AcountState.OPEN)
                .creationDate(LocalDate.now())
                .build()
        );

        accountService.creatAccount(PrivateCurrentAccount.builder()
                .customerId(customer2.getId())
                .accountNumber("00002")
                .accountBalance(BigDecimal.valueOf(3000))
                .accountState(AcountState.OPEN)
                .creationDate(LocalDate.now())
                .build()
        );

        accountService.creatAccount(PrivateCurrentAccount.builder()
                .customerId(customer3.getId())
                .accountNumber("00003")
                .accountBalance(BigDecimal.valueOf(3000))
                .accountState(AcountState.OPEN)
                .creationDate(LocalDate.now())
                .build()
        );

        System.out.println(accountService.listAllAccountsByCustomer(customer2.getId()).toString());



    }}
