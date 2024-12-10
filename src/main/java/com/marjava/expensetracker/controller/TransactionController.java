package com.marjava.expensetracker.controller;
import com.marjava.expensetracker.model.Debt;
import com.marjava.expensetracker.model.Transaction;
import com.marjava.expensetracker.service.DebtService;
import com.marjava.expensetracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DebtService debtService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Transaction> allTransactions = transactionService.getAllTransactions();
        List<Debt> debts = debtService.getAllDebts(); 
        BigDecimal totalAmount = allTransactions.stream()
        .map(Transaction -> Transaction.getType() == Transaction.getType().EXPENSE ? Transaction.getAmount().negate() : Transaction.getAmount())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    
        model.addAttribute("transactions", allTransactions);
        model.addAttribute("totalBalance", totalAmount);
        model.addAttribute("debts", debts);
        return "index";
    }

    @GetMapping("/addTransaction")
    public String showAddTransactionPage(Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        return "add-transaction";
    }

    @PostMapping("/saveTransaction")
    public String saveTransaction(@ModelAttribute("transaction") Transaction transaction, Model model) {
        try {
            if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                model.addAttribute("errorMessage", "Amount must be positive and greater than zero.");
                return "add-transaction"; 
            }
    
            if (transaction.getDebts() != null && !transaction.getDebts().isEmpty()) {
                for (Debt debt : transaction.getDebts()) {
                    debt.setTransaction(transaction);  
                }
            }
    
            transactionService.saveTransaction(transaction);
            return "redirect:/"; 
    
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "add-transaction"; // Go back with error message
        }
    }

    @GetMapping("/editTransaction/{id}")
    public String showUpdateTransactionPage(@PathVariable("id") long id, Model model) {
        Transaction transaction = transactionService.getTransactionById(id);
        model.addAttribute("transaction", transaction);
        return "update-transaction";
    }

    @PostMapping("/updateTransaction/{id}")
    public String updateTransaction(@PathVariable("id") long id, 
                                @ModelAttribute("transaction") Transaction transaction, 
                                Model model) {
        try {
            Transaction existingTransaction = transactionService.getTransactionById(id);
            if (existingTransaction == null) {
                throw new RuntimeException("Transaction not found");
            }

            // Update basic fields
            existingTransaction.setDescription(transaction.getDescription());
            existingTransaction.setAmount(transaction.getAmount());
            existingTransaction.setType(transaction.getType());

            existingTransaction.getDebts().clear();
            if (transaction.getDebts() != null) {
                for (Debt debt : transaction.getDebts()) {
                    debt.setTransaction(existingTransaction);
                    existingTransaction.getDebts().add(debt);
                }
            }

            transactionService.saveTransaction(existingTransaction);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating transaction: " + e.getMessage());
            return "update-transaction";
        }
    }

    @GetMapping("/deleteTransaction/{id}")
    public String deleteTransaction(@PathVariable("id") long id) {
        transactionService.deleteTransactionById(id);
        return "redirect:/";
    }
}
