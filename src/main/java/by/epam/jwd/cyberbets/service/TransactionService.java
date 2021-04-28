package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.domain.Transaction;
import by.epam.jwd.cyberbets.domain.TransactionType;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TransactionService {
    List<Transaction> findAllTransactionsByAccountId(int accountId) throws ServiceException;
    List<Transaction> findAllTransactionsByPeriod(int daysNumber) throws ServiceException;
    Optional<Transaction> findTransactionById(int transactionId) throws ServiceException;
    BigDecimal getTransactionsAmountByPeriod(int daysNumber, TransactionType transactionType) throws ServiceException;
    Map<String, BigDecimal> getDayTransactionsAmountByPeriod(int daysNumber, TransactionType transactionType) throws ServiceException;
}
