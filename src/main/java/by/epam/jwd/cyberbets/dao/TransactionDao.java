package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {
    List<Transaction> findAllTransactionsByAccountId(int accountId) throws DaoException;
    Optional<Transaction> findTransactionById(int transactionId) throws DaoException;
    int createTransaction(Transaction transaction) throws DaoException;
    void updateTransaction(Transaction transaction) throws DaoException;
    void deleteTransaction(int transactionId) throws DaoException;
}
