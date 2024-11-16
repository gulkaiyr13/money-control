package alatoo.edu.kg.money.control.repository;


import alatoo.edu.kg.money.control.entity.Transaction;
import alatoo.edu.kg.money.control.entity.User;
import alatoo.edu.kg.money.control.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByType(TransactionType type);
    List<Transaction> findByDate(LocalDate date);
    List<Transaction> findByTypeAndDate(TransactionType type, LocalDate date);
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndType(User user, TransactionType type);
    List<Transaction> findByUserAndDate(User user, LocalDate date);
    List<Transaction> findByUserAndTypeAndDate(User user, TransactionType type, LocalDate date);
}
