package alatoo.edu.kg.money.control.service;

import alatoo.edu.kg.money.control.entity.User;
import alatoo.edu.kg.money.control.entity.Transaction;
import alatoo.edu.kg.money.control.mapper.TransactionMapper;
import alatoo.edu.kg.money.control.model.TransactionDTO;
import alatoo.edu.kg.money.control.model.TransactionType;
import alatoo.edu.kg.money.control.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private UserService userService;

    public TransactionDTO createTransaction(Long userId, TransactionDTO transactionDTO) {
        Optional<User> userOpt = userService.getUserEntityById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setUser(userOpt.get());
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toDTO(savedTransaction);
    }

    public List<TransactionDTO> getAllTransactions(TransactionType type, LocalDate date, Long userId) {
        User user;
        if (userId != null) {
            Optional<User> userOpt = userService.getUserEntityById(userId);
            if (userOpt.isEmpty()) {
                throw new RuntimeException("User not found with id: " + userId);
            }
            user = userOpt.get();
        } else {
            user = null;
        }

        List<Transaction> transactions = transactionRepository.findAll();

        if (user != null) {
            transactions = transactions.stream()
                    .filter(t -> t.getUser().equals(user))
                    .collect(Collectors.toList());
        }

        if (type != null) {
            transactions = transactions.stream()
                    .filter(t -> t.getType() == type)
                    .collect(Collectors.toList());
        }

        if (date != null) {
            transactions = transactions.stream()
                    .filter(t -> t.getDate().equals(date))
                    .collect(Collectors.toList());
        }

        return transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TransactionDTO> getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toDTO);
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(id);
        if (transactionOpt.isEmpty()) {
            throw new RuntimeException("Transaction not found with id: " + id);
        }

        Transaction transaction = transactionOpt.get();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType(transactionDTO.getType());
        transaction.setDate(transactionDTO.getDate());
        transaction.setDescription(transactionDTO.getDescription());

        if (transactionDTO.getUserId() != null && !transaction.getUser().getId().equals(transactionDTO.getUserId())) {
            Optional<User> userOpt = userService.getUserEntityById(transactionDTO.getUserId());
            if (userOpt.isEmpty()) {
                throw new RuntimeException("User not found with id: " + transactionDTO.getUserId());
            }
            transaction.setUser(userOpt.get());
        }

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toDTO(updatedTransaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
