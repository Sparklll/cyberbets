package by.epam.jwd.cyberbets.dao.impl.transactional;

import by.epam.jwd.cyberbets.dao.AccountDao;
import by.epam.jwd.cyberbets.dao.TransactionDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.dao.impl.CompositeDao;
import by.epam.jwd.cyberbets.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

public class AccountManager {
    private static final Logger logger = LoggerFactory.getLogger(AccountManager.class);

    public void performDeposit(int accountId, BigDecimal amount) throws DaoException {
        performTransaction(TransactionType.DEPOSIT, accountId, amount);
    }

    public void performWithdraw(int accountId, BigDecimal amount) throws DaoException {
        performTransaction(TransactionType.WITHDRAW, accountId, amount);
    }

    private void performTransaction(TransactionType transactionType, int accountId, BigDecimal amount) throws DaoException {
        Connection transactionConnection = ConnectionPool.INSTANCE.getConnection();
        try (transactionConnection) {
            transactionConnection.setAutoCommit(false);

            CompositeDao compositeDao = new CompositeDao.Builder(transactionConnection)
                    .withAccountDao()
                    .withTransactionDao()
                    .build();

            final AccountDao accountDao = compositeDao.getAccountDao();
            final TransactionDao transactionDao = compositeDao.getTransactionDao();

            Optional<Account> accountOptional = accountDao.findAccountById(accountId);
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                BigDecimal accountBalance = account.getBalance();

                if(accountBalance.compareTo(amount) >= 0) {
                    Instant currentDate = Instant.now();

                    Transaction transaction = null;
                    if(transactionType == TransactionType.WITHDRAW) {
                        transaction = new Transaction(accountId, TransactionType.WITHDRAW, amount, currentDate);
                        accountDao.subtractFromAccountBalance(accountId, amount);
                    } else if(transactionType == TransactionType.DEPOSIT) {
                        transaction = new Transaction(accountId, TransactionType.DEPOSIT, amount, currentDate);
                        accountDao.addToAccountBalance(accountId, amount);
                    }
                    transactionDao.createTransaction(transaction);
                }
            }
            transactionConnection.commit();
        } catch (Exception e) {
            try {
                transactionConnection.rollback();
                throw new DaoException(e);
            } catch (SQLException sqlException) {
                throw new DaoException(sqlException);
            }
        } finally {
            try {
                if(transactionConnection != null) {
                    transactionConnection.setAutoCommit(true);
                }
            } catch (SQLException sqlException) {
                logger.error(sqlException.getMessage() ,sqlException);
            }
        }
    }
}
