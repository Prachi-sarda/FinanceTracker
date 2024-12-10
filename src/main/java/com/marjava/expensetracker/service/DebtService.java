package com.marjava.expensetracker.service;
import com.marjava.expensetracker.model.Debt;
import com.marjava.expensetracker.repository.DebtRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class DebtService {
    @Autowired
    private DebtRepository debtRepo;

    public void saveDebt(Debt debt) {
        debtRepo.save(debt);
    }
    public List<Debt> getAllDebts() {
        return debtRepo.findAll();
    }
    public List<Debt> getDebtsByTransactionId(Long transactionId) {
        return debtRepo.findByTransactionId(transactionId);
    }

    public Debt getDebtById(Long id) {
        return debtRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Debt not found"));
    }

    @Transactional
    public void updateDebt(Long id, Debt updatedDebt) {
        Debt debt = getDebtById(id);
        debt.setDebtorName(updatedDebt.getDebtorName());
        debt.setAmount(updatedDebt.getAmount());
        debt.setType(updatedDebt.getType());
        debt.setDescription(updatedDebt.getDescription());
        debt.setDueDate(updatedDebt.getDueDate());
        debtRepo.save(debt);
    }

    @Transactional
    public void deleteDebt(Long id) {
        debtRepo.deleteById(id);
    }
}