package com.infoeste.codecash.service;

import com.infoeste.codecash.dto.CreateTransactionInput;
import com.infoeste.codecash.model.Account;
import com.infoeste.codecash.model.Transaction;
import com.infoeste.codecash.repository.AccountRepository;
import com.infoeste.codecash.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(CreateTransactionInput createTransactionInput) {
       Account payer = accountRepository.findById(createTransactionInput.payerAccountId())
               .orElseThrow(() -> new IllegalArgumentException("payer account not found"));

       Account reciever = accountRepository.findById(createTransactionInput.payeeAccountId())
            .orElseThrow(() -> new IllegalArgumentException("peyee account not found"));

       if (payer.getBalance().compareTo(createTransactionInput.amount()) <0) {
           throw  new IllegalArgumentException("insufficient found");
       }

       if (payer.getId().equals(reciever.getId())) {
           throw new IllegalArgumentException("payer and reciever account cannot be the same");
       }

       payer.setBalance(
               payer.getBalance().subtract(createTransactionInput.amount())
       );

       reciever.setBalance(
               reciever.getBalance().add(createTransactionInput.amount())
       );

       accountRepository.saveAll(List.of(payer, reciever));

       Transaction transaction = new Transaction();
       transaction.setAmount(createTransactionInput.amount());
       transaction.setPayerAccount(payer);
       transaction.setPayeeAccount(reciever);
       transaction.setTransactionTime(Instant.now());

       return  transactionRepository.save(transaction);

    }
}
