package com.prograssoft.shahad.application.configurations;

import com.prograssoft.shahad.business.model.*;
import com.prograssoft.shahad.business.service.*;
import com.prograssoft.shahad.business.service.interfaces.AuthService;
import com.prograssoft.shahad.business.service.limit.CorporateAccountLimitStrategy;
import com.prograssoft.shahad.business.service.limit.PrivateCurrentLimitStrategy;
import com.prograssoft.shahad.business.service.limit.PrivateSavingLimitStrategy;
import com.prograssoft.shahad.business.service.limit.WithdrawLimitStrategy;
import com.prograssoft.shahad.business.storage.repository.*;
import com.prograssoft.shahad.business.storage.repository.interfaces.AccountRepository;
import com.prograssoft.shahad.business.storage.repository.interfaces.CustomerRepository;
import com.prograssoft.shahad.business.storage.repository.interfaces.TransactionRepository;
import com.prograssoft.shahad.business.service.interfaces.AccountService;
import com.prograssoft.shahad.business.service.interfaces.CustomerService;
import com.prograssoft.shahad.business.service.interfaces.TransactionService;
import com.prograssoft.shahad.business.storage.repository.interfaces.UserRepository;
import com.prograssoft.shahad.business.storage.repository.jpa.interfaces.*;
import com.prograssoft.shahad.business.storage.repository.sql.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class BankingSystemConfigurations {
    @Bean(name = "individualCustomerRepository")
    @ConditionalOnProperty(name = "storage.type", havingValue = "mem")
    public CustomerRepository<IndividualCustomer> individualCustomerRepositry() {
        return new IndividualCustomerRepository();

    }

    @Bean(name = "corporateCustomerRepository")
    @ConditionalOnProperty(name = "storage.type", havingValue = "mem")
    public CustomerRepository<CorporateCustomer> corporateCustomerRepositry() {
        return new CorporateCustomerRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "storage.type", havingValue = "mem")
    public AccountRepository accountRepository() {
        return new AccountRepositoryImpl();
    }

    @Bean
    @ConditionalOnProperty(name = "storage.type", havingValue = "mem")
    public TransactionRepository transactionRepository() {
        return new TransactionRepositoryImpl();
    }

    @Bean
    public CustomerService customerService(@Qualifier("individualCustomerRepository") CustomerRepository<IndividualCustomer> individualCustomer,
                                           @Qualifier("corporateCustomerRepository") CustomerRepository<CorporateCustomer> corporateCustomer) {
        return new CustomerServiceImpl(individualCustomer, corporateCustomer);
    }

    @Bean(name = "individualCustomerRepository")
    @ConditionalOnProperty(name = "storage.type", havingValue = "db")
    public CustomerRepository<IndividualCustomer> sqlIndividualCustomerRepository(IndividualCustomerJpaRepository jpaRepository) {
        return new SqlIndividualCustomerRepository(jpaRepository);
    }

    @Bean(name = "corporateCustomerRepository")
    @ConditionalOnProperty(name = "storage.type", havingValue = "db")
    public CustomerRepository<CorporateCustomer> sqlCorporateCustomerRepository(CorporateCustomerJpaRepository jpaRepository) {
        return new SqlCorporateCustomerRepository(jpaRepository);
    }

    @Bean
    @ConditionalOnProperty(name = "storage.type", havingValue = "db")
    public AccountRepository sqlAccountRepository(AccountJpaRepository jpaRepository) {
        return new SqlAccountRepository(jpaRepository);
    }

    @Bean
    @ConditionalOnProperty(name = "storage.type", havingValue = "db")
    public TransactionRepository sqlTransactionRepository(TransactionJpaRepository jpaRepository) {
        return new SqlTransactionRepository(jpaRepository);
    }
    @Bean
    @ConditionalOnProperty(name="storage.type", havingValue = "db")
    public UserRepository sqlUserRepository(UserJpaRepository  jpaRepository) {
        return new SqlUserRepository(jpaRepository);
    }


    @Bean
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountServiceImpl(accountRepository);
    }

    @Bean
    public TransactionService transactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            List<WithdrawLimitStrategy> withdrawLimitStrategies
    ) {
        Map<Class<? extends Account>, WithdrawLimitStrategy> withdrawLimitStrategyMap = withdrawLimitStrategies.stream()
                .collect(Collectors.toMap(WithdrawLimitStrategy::getAccountType, s -> s));
        withdrawLimitStrategyMap.put(PrivateCurrentAccount.class, new PrivateCurrentLimitStrategy());
        withdrawLimitStrategyMap.put(CorporateAccount.class, new CorporateAccountLimitStrategy());
        withdrawLimitStrategyMap.put(PrivateSavingAccount.class, new PrivateSavingLimitStrategy());

        return new TransactionServiceImpl(
                accountRepository,
                transactionRepository,
                withdrawLimitStrategyMap
        );
    }
    @Bean
    public AuthService authService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        return new AuthServiceImpl(userRepository,passwordEncoder,jwtService);
    }
}