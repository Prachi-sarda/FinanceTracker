package com.marjava.expensetracker.repository;
import com.marjava.expensetracker.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByTransactionId(Long transactionId);
}
