package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.domain.Transaction;
import by.epam.jwd.cyberbets.domain.dto.TransactionDto;

import java.util.List;

public interface TransactionDao {
    List<Transaction> findTransactions(int limit);
    List<Transaction> findTransactionsByAccountId(int accountId);
    List<Transaction> findTransactionsByAccountId(int accountId, int limit);
    void createTransaction(TransactionDto transactionDto);
}
