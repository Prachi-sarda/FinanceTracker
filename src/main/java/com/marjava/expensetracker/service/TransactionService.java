package com.marjava.expensetracker.service;
import com.marjava.expensetracker.model.Transaction;
import com.marjava.expensetracker.repository.TransactionRepository;

import jakarta.transaction.Transactional;

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

    @Transactional
    public Transaction saveTransaction(Transaction transaction) {
        if (transaction.getDebts() != null) {
            transaction.getDebts().forEach(debt -> debt.setTransaction(transaction));
        }
        return transactionRepo.save(transaction);
    }
    
    public Transaction getTransactionById(Long id) {
        return transactionRepo.findById(id).orElse(null);
    }

    public void deleteTransactionById(Long id) {
        transactionRepo.deleteById(id);
    }
}
