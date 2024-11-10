package com.example.expensetracker.controller;
import com.example.expensetracker.model.Transaction;
import com.example.expensetracker.service.TransactionService;
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
    private TransactionService TransactionService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Transaction> allTransactions = TransactionService.getAllTransactions();
        BigDecimal totalAmount = allTransactions.stream()
                .map(Transaction -> Transaction.getType() == Transaction.getType().EXPENSE ? Transaction.getAmount().negate() : Transaction.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("transactions", allTransactions);
        model.addAttribute("totalBalance", totalAmount);
        return "index";
    }

    @GetMapping("/addTransaction")
    public String showAddTransactionPage(Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("expense", transaction);
        return "add-transaction";
    }

    @PostMapping("/saveTransaction")
    public String saveTransaction(@ModelAttribute("transaction") Transaction transaction, Model model) {
        TransactionService.saveTransaction(transaction);
        List<Transaction> allTransactions = TransactionService.getAllTransactions();
        model.addAttribute("transactions", allTransactions);
        return "redirect:/";
    }

    @GetMapping("/editTransaction/{id}")
    public String showUpdateTransactionPage(@PathVariable("id") long id, Model model) {
        Transaction transaction = TransactionService.getTransactionById(id);
        model.addAttribute("expense", transaction);
        return "update-transaction";
    }

    @PostMapping("/updateTransaction/{id}")
    public String updateTransaction(@PathVariable("id") long id, @ModelAttribute("transaction") Transaction transaction, Model model) {
        Transaction existingTransaction = TransactionService.getTransactionById(id);
        existingTransaction.setDescription(transaction.getDescription());
        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setType(transaction.getType());
        TransactionService.saveTransaction(existingTransaction);
        return "redirect:/";
    }

    @GetMapping("/deleteTransaction/{id}")
    public String deleteTransaction(@PathVariable("id") long id) {
        TransactionService.deleteTransactionById(id);
        return "redirect:/";
    }
}
