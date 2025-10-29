package com.infoeste.codecash.service;

import com.infoeste.codecash.dto.CreateTransactionInput;
import com.infoeste.codecash.model.Account;
import com.infoeste.codecash.model.Transaction;
import com.infoeste.codecash.repository.AccountRepository;
import com.infoeste.codecash.repository.TransactionRepository;
import org.springframework.stereotype.Service;

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


    }


}
