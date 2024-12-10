package com.marjava.expensetracker.controller;

import com.marjava.expensetracker.model.Debt;
import com.marjava.expensetracker.model.DebtType;
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
public class DebtController {

    @Autowired
    private DebtService debtService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/saveTransactionWithDebts")
    public String saveTransactionWithDebts(@ModelAttribute("transaction") Transaction transaction, 
                                            @ModelAttribute("debts") List<Debt> debts, Model model) {

        try {
            // Validate the transaction amount
            if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                model.addAttribute("errorMessage", "Amount must be positive and greater than zero.");
                return "add-transaction";  // Go back to the add transaction page with an error message
            }

            // Save the transaction
            transactionService.saveTransaction(transaction);

            // Save each debt linked to the transaction
            for (Debt debt : debts) {
                debt.setTransaction(transaction);  // Set the transaction reference
                debtService.saveDebt(debt);         // Save the debt
            }

            // Redirect to the home page after saving
            return "redirect:/";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while saving the transaction and debts.");
            return "add-transaction";  // Go back with error message
        }
    }

    @GetMapping("/addDebt")
    public String showAddDebtForm(Model model) {
        Debt debt = new Debt();
        model.addAttribute("debt", debt);
        System.out.println("Accessing add debt form"); // Add this debug line
        return "add-debt";
    }

    @PostMapping("/saveDebt")
    public String saveDebt(@ModelAttribute("debt") Debt debt) {
        debtService.saveDebt(debt);
        return "redirect:/";
    }

    @GetMapping("/editDebt/{id}")
    public String showEditDebtForm(@PathVariable Long id, Model model) {
        Debt debt = debtService.getDebtById(id);
        model.addAttribute("debt", debt);
        model.addAttribute("DebtType", DebtType.class);
        return "update-debt";
    }

    @PostMapping("/updateDebt/{id}")
    public String updateDebt(@PathVariable Long id, @ModelAttribute Debt debt) {
        debtService.updateDebt(id, debt);
        return "redirect:/";
    }

    @GetMapping("/deleteDebt/{id}")
    public String deleteDebt(@PathVariable Long id) {
        debtService.deleteDebt(id);
        return "redirect:/";
    }
}

