package com.springboot.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.banking.entity.Account;
import com.springboot.banking.entity.Transaction;
import com.springboot.banking.service.BankingService;

@RestController
@RequestMapping("/api/banking")
public class BankingController {
    @Autowired
    private BankingService bankingService;

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(bankingService.createAccount(account));
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(bankingService.getAllAccounts());
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferFunds(@RequestParam Long fromAccountId, @RequestParam Long toAccountId, @RequestParam Double amount) {
        bankingService.transferFunds(fromAccountId, toAccountId, amount);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long accountId) {
        return ResponseEntity.ok(bankingService.getTransactionHistory(accountId));
    }
}
