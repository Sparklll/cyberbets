package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.AccountDao;
import by.epam.jwd.cyberbets.dao.BetDao;
import by.epam.jwd.cyberbets.dao.EventResultDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.*;
import by.epam.jwd.cyberbets.domain.dto.BetDto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class BetManager {
    BetManager() {

    }

    public void createBet(BetDto betDto) throws DaoException {
        Connection transactionConnection = ConnectionPool.INSTANCE.getConnection();
        try (transactionConnection) {
            transactionConnection.setAutoCommit(false);

            CompositeDao compositeDao = new CompositeDao.Builder(transactionConnection)
                    .withEventResultDao()
                    .withAccountDao()
                    .withBetDao()
                    .build();
            final EventResultDao eventResultDao = compositeDao.getEventResultDao();
            final AccountDao accountDao = compositeDao.getAccountDao();
            final BetDao betDao = compositeDao.getBetDao();

            int eventResultId = betDto.eventResultId();
            int accountId = betDto.accountId();
            BigDecimal betAmount = betDto.amount();

            Optional<Account> accountOptional = accountDao.findAccountById(accountId);
            Optional<EventResult> eventResultOptional = eventResultDao.findEventResultById(eventResultId);
            boolean isThereAccountBetOnResult = betDao.findBetByAccountIdAndEventResultId(accountId, eventResultId).isEmpty();
            if (accountOptional.isPresent()
                    && eventResultOptional.isPresent()
                    && !isThereAccountBetOnResult) {
                Account account = accountOptional.get();
                EventResult eventResult = eventResultOptional.get();
                BigDecimal accountBalance = account.getBalance();
                ResultStatus resultStatus = eventResult.getResultStatus();

                if (resultStatus == ResultStatus.UNBLOCKED && accountBalance.compareTo(betAmount) >= 0) {
                    accountDao.subtractFromAccountBalance(accountId, betAmount);
                    betDao.createBet(betDto);
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
        }
    }

    public void updateBet(BetDto betDto) throws DaoException {
        Connection transactionConnection = ConnectionPool.INSTANCE.getConnection();
        try (transactionConnection) {
            transactionConnection.setAutoCommit(false);

            CompositeDao compositeDao = new CompositeDao.Builder(transactionConnection)
                    .withEventResultDao()
                    .withAccountDao()
                    .withBetDao()
                    .build();
            final EventResultDao eventResultDao = compositeDao.getEventResultDao();
            final AccountDao accountDao = compositeDao.getAccountDao();
            final BetDao betDao = compositeDao.getBetDao();

            int eventResultId = betDto.eventResultId();
            int accountId = betDto.accountId();
            BigDecimal newBetAmount = betDto.amount();

            Optional<Account> accountOptional = accountDao.findAccountById(accountId);
            Optional<EventResult> eventResultOptional = eventResultDao.findEventResultById(eventResultId);
            Optional<Bet> placedBetOptional = betDao.findBetByAccountIdAndEventResultId(accountId, eventResultId);
            if (accountOptional.isPresent()
                    && eventResultOptional.isPresent()
                    && placedBetOptional.isPresent()) {

                Account account = accountOptional.get();
                EventResult eventResult = eventResultOptional.get();
                Bet placedBet = placedBetOptional.get();
                BigDecimal placedBetAmount = placedBet.getAmount();

                BigDecimal accountBalance = account.getBalance();
                ResultStatus resultStatus = eventResult.getResultStatus();

                if (resultStatus == ResultStatus.UNBLOCKED
                        && accountBalance.add(placedBetAmount) .compareTo(newBetAmount) >= 0) {
                    BigDecimal newAccountBalance = accountBalance
                            .add(placedBetAmount)
                            .subtract(newBetAmount);
                    accountDao.updateAccountBalance(accountId, newAccountBalance);
                    betDao.updateBet(placedBet);
                }
            }
            transactionConnection.commit();
        } catch (Exception e) {
            try {
                transactionConnection.rollback();
            } catch (SQLException sqlException) {
                throw new DaoException(sqlException);
            }
        }
    }

    public void deleteBet(BetDto betDto) throws DaoException {
        Connection transactionConnection = ConnectionPool.INSTANCE.getConnection();
        try (transactionConnection) {
            transactionConnection.setAutoCommit(false);


            transactionConnection.commit();
        } catch (Exception e) {
            try {
                transactionConnection.rollback();
            } catch (SQLException sqlException) {
                throw new DaoException(sqlException);
            }
        }
    }
}
