package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.TransactionDao;
import by.epam.jwd.cyberbets.domain.Transaction;
import by.epam.jwd.cyberbets.domain.dto.TransactionDto;

import java.util.List;

public class TransactionDaoImpl implements TransactionDao {
    @Override
    public List<Transaction> findTransactions(int limit) {
        return null;
    }

    @Override
    public List<Transaction> findTransactionsByAccountId(int accountId) {
        return null;
    }

    @Override
    public List<Transaction> findTransactionsByAccountId(int accountId, int limit) {
        return null;
    }

    @Override
    public void createTransaction(TransactionDto transactionDto) {

    }
}
