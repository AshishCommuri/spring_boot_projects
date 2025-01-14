package com.springboot.banking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.banking.entity.Account;
import com.springboot.banking.entity.Transaction;
import com.springboot.banking.repository.AccountRepository;
import com.springboot.banking.repository.TransactionRepository;

@Service
public class BankingService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with ID " + accountId));
    }

   

    public void transferFunds(Long fromAccountId, Long toAccountId, Double amount) {
        // Fetch accounts
        Account fromAccount = getAccountById(fromAccountId);
        Account toAccount = getAccountById(toAccountId);

        // Check for sufficient balance
        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in account ID " + fromAccountId);
        }

        // Update account balances
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        // Save updated accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Create transaction date
        LocalDateTime transactionDate = LocalDateTime.now();

        // Create transaction objects
        Transaction debitTransaction = new Transaction(null,fromAccount,amount,"DEBIT",transactionDate);

        Transaction creditTransaction = new Transaction(null,toAccount,amount,"CREDIT",transactionDate);

        // Save transactions
        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);
    }


    public List<Transaction> getTransactionHistory(Long accountId) {
        return transactionRepository.findByAccountAccountId(accountId);
    }
}
