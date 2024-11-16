package alatoo.edu.kg.money.control.controller;

import alatoo.edu.kg.money.control.model.TransactionDTO;
import alatoo.edu.kg.money.control.model.TransactionType;
import alatoo.edu.kg.money.control.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createTransaction(@PathVariable Long userId,
                                               @Valid @RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionDTO savedTransaction = transactionService.createTransaction(userId, transactionDTO);
            return ResponseEntity.ok(savedTransaction);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Long userId
    ) {
        try {
            LocalDate parsedDate = null;
            if (date != null) {
                parsedDate = LocalDate.parse(date);
            }

            List<TransactionDTO> transactions = transactionService.getAllTransactions(type, parsedDate, userId);
            return ResponseEntity.ok(transactions);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        Optional<TransactionDTO> transactionOpt = transactionService.getTransactionById(id);
        return transactionOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id,
                                               @Valid @RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionDTO updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
            return ResponseEntity.ok(updatedTransaction);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteTransaction(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
