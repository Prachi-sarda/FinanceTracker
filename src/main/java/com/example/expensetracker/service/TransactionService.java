package com.example.expensetracker.service;

import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepo;

    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepo.save(transaction);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepo.findById(id).orElse(null);
    }

    public void deleteTransactionById(Long id) {
        transactionRepo.deleteById(id);
    }
}
