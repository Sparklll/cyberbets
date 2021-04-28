package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.TransactionDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.domain.Transaction;
import by.epam.jwd.cyberbets.domain.TransactionType;
import by.epam.jwd.cyberbets.service.TransactionService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
            List<Transaction> periodTransactions = transactionDao.findAllTransactionsByPeriod(daysNumber);
            return periodTransactions.stream()
                    .filter(t -> t.getTransactionType() == transactionType)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, BigDecimal> getDayTransactionsAmountByPeriod(int daysNumber, TransactionType transactionType) throws ServiceException {
        try {
            Instant currentDate = Instant.now();
            Map<String, BigDecimal> transactionsDateAndAmount = new LinkedHashMap<>();
            Map<Integer, BigDecimal> periodTransactions = transactionDao.findAllTransactionsByPeriod(daysNumber).stream()
                    .filter(t -> t.getTransactionType() == transactionType)
                    .collect(Collectors.groupingBy(t -> (int) (daysNumber - Duration.between(t.getDate(), currentDate).toDays()),
                            Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));

            for(int i = 0; i < daysNumber; i++) {
                String transactionsDate = currentDate.minus(daysNumber - i, ChronoUnit.DAYS)
                        .atZone(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ofPattern("dd.MM"));
                transactionsDateAndAmount.put(transactionsDate, periodTransactions.getOrDefault(i, BigDecimal.ZERO));
            }

            return transactionsDateAndAmount;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
