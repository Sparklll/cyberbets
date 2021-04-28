package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.TransactionDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.domain.Transaction;
import by.epam.jwd.cyberbets.domain.TransactionType;
import by.epam.jwd.cyberbets.service.TransactionService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao transactionDao = DaoProvider.INSTANCE.getTransactionDao();

    TransactionServiceImpl() {

    }

    @Override
    public List<Transaction> findAllTransactionsByAccountId(int accountId) throws ServiceException {
        try {
            return transactionDao.findAllTransactionsByAccountId(accountId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Transaction> findAllTransactionsByPeriod(int daysNumber) throws ServiceException {
        try {
            return transactionDao.findAllTransactionsByPeriod(daysNumber);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Transaction> findTransactionById(int transactionId) throws ServiceException {
        try {
            return transactionDao.findTransactionById(transactionId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BigDecimal getTransactionsAmountByPeriod(int daysNumber, TransactionType transactionType) throws ServiceException {
        try {
            List<Transaction> transactions = transactionDao.findAllTransactionsByPeriod(daysNumber);
            return transactions.stream()
                    .filter(t -> t.getTransactionType() == transactionType)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
