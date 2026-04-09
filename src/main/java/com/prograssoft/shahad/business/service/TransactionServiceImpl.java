package com.prograssoft.shahad.business.service;

import com.prograssoft.shahad.business.exception.ValidationException;
import com.prograssoft.shahad.business.mapper.TransactionJpaMapper;
import com.prograssoft.shahad.business.model.*;
import com.prograssoft.shahad.business.model.enums.AcountState;
import com.prograssoft.shahad.business.storage.repository.interfaces.AccountRepository;
import com.prograssoft.shahad.business.storage.repository.interfaces.TransactionRepository;
import com.prograssoft.shahad.business.service.interfaces.TransactionService;
import com.prograssoft.shahad.business.service.limit.WithdrawLimitStrategy;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final Map<Class<? extends Account>, WithdrawLimitStrategy> withdrawLimitStrategyMap;

    public TransactionServiceImpl(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            Map<Class<? extends Account>, WithdrawLimitStrategy> withdrawLimitStrategies
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.withdrawLimitStrategyMap = withdrawLimitStrategies;
    }

    @Override
    @Transactional
    public void deposit(String accountNumber, BigDecimal amount) {
        validateAmountMustBePositive(amount);
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

        checkAccountState(account);
        account.setAccountBalance(account.getAccountBalance().add(amount));
        accountRepository.saveAccount(account);

        DepositTransaction depositTransaction = DepositTransaction.builder()
                .accountNumber(accountNumber)
                .type("DEPOSIT")
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(depositTransaction);
    }

    @Override
    @Transactional
    public void withdraw(String accountNumber, BigDecimal amount) {
        validateAmountMustBePositive(amount);
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

        checkAccountState(account);

        if (account.getAccountBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        WithdrawLimitStrategy strategy = withdrawLimitStrategyMap.get(account.getClass());
        if (strategy == null) {
            throw new RuntimeException("No withdraw limit strategy found for: " + account.getClass().getSimpleName());
        }

        strategy.validate(account,amount);

        account.setAccountBalance(account.getAccountBalance().subtract(amount));
        accountRepository.saveAccount(account);

        WithdrawalTransaction withdrawalTransaction = WithdrawalTransaction.builder()
                .accountNumber(accountNumber)
                .type("WITHDRAW")
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .build();


        transactionRepository.save(withdrawalTransaction);
    }

    @Override
    @Transactional
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        validateAmountMustBePositive(amount);

        if (toAccountNumber.equals(fromAccountNumber)) {
            throw new ValidationException("Cannot transfer to the same account");
        }

        Account from = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + fromAccountNumber));
        Account to = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + toAccountNumber));

        checkAccountState(from);
        checkAccountState(to);

        if (from.getAccountBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        WithdrawLimitStrategy strategy = withdrawLimitStrategyMap.get(from.getClass());
        if (strategy == null) {
            throw new RuntimeException("No withdraw limit strategy found for: " + from.getClass().getSimpleName());
        }
        strategy.validate(from,amount);

        from.setAccountBalance(from.getAccountBalance().subtract(amount));
        accountRepository.saveAccount(from);

        to.setAccountBalance(to.getAccountBalance().add(amount));
        accountRepository.saveAccount(to);

        WithdrawalTransaction withdrawalTransaction = WithdrawalTransaction.builder()
                .accountNumber(fromAccountNumber)
                .type("WITHDRAW")
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(withdrawalTransaction);

        DepositTransaction depositTransaction = DepositTransaction.builder()
                .accountNumber(toAccountNumber)
                .type("DEPOSIT")
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(depositTransaction);


        TransferTransaction transferTransaction = TransferTransaction.builder()
                .accountNumber(fromAccountNumber)
                .accountNumberTo(toAccountNumber)
                .amount(amount)
                .type("TRANSFER")
                .transactionDate(LocalDateTime.now())
                .build();
        transactionRepository.save(transferTransaction);
    }

    @Override
    public List<Transaction> listTransactionForAccount(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber)
                .stream()
                .map(TransactionJpaMapper::toTransaction)
                .collect(Collectors.toList());
    }

    private static void checkAccountState(Account account) {
        if (account.getAccountState() != AcountState.OPEN) {
            throw new RuntimeException("The account is closed");
        }
    }

    private static void validateAmountMustBePositive(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be positive");
        }    }
}
