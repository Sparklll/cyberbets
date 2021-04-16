package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.AccountDao;
import by.epam.jwd.cyberbets.dao.BetDao;
import by.epam.jwd.cyberbets.dao.EventDao;
import by.epam.jwd.cyberbets.dao.EventResultDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.Bet.Upshot;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.ResultStatus;
import by.epam.jwd.cyberbets.domain.dto.EventDto;
import by.epam.jwd.cyberbets.util.CoefficientCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventManager {
    private static final Logger logger = LoggerFactory.getLogger(EventManager.class);

    EventManager() {

    }

    public int createEvent(EventDto eventDto, List<EventResult> eventResults) throws DaoException {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try (connection) {
            connection.setAutoCommit(false);

            CompositeDao compositeDao = new CompositeDao(connection);
            final EventDao eventDao = compositeDao.getEventDao();
            final EventResultDao eventResultDao = compositeDao.getEventResultDao();

            int eventId = eventDao.createEvent(eventDto);
            for (EventResult eventResult : eventResults) {
                eventResult.setEventId(eventId);
                eventResultDao.createEventResult(eventResult);
            }
            connection.commit();
            return eventId;
        } catch (Exception e) {
            try {
                connection.rollback();
                throw new DaoException(e);
            } catch (SQLException sqlException) {
                throw new DaoException(sqlException);
            }
        }
    }

    public void updateEvent(EventDto eventDto, List<EventResult> eventResults) throws DaoException {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try (connection) {
            connection.setAutoCommit(false);

            CompositeDao compositeDao = new CompositeDao(connection);
            final EventDao eventDao = compositeDao.getEventDao();
            final EventResultDao eventResultDao = compositeDao.getEventResultDao();

            int eventId = eventDto.eventId();

            eventDao.updateEvent(eventDto);
            List<EventResult> databaseEventResults = eventResultDao.findAllByEventId(eventId);

            // in case of change eventFormat /|:non-effective:|\
            List<EventResult> toAddEventResults = eventResults.stream()
                    .filter(er -> !databaseEventResults.stream()
                            .map(EventResult::getEventOutcomeType)
                            .collect(Collectors.toSet())
                            .contains(er.getEventOutcomeType()))
                    .collect(Collectors.toList());

            for (EventResult toAddResult : toAddEventResults) {
                eventResultDao.createEventResult(toAddResult);
            }

            for (EventResult databaseEventResult : databaseEventResults) {
                Optional<EventResult> eventResultOptional = eventResults.stream()
                        .filter(er -> er.getEventOutcomeType() == databaseEventResult.getEventOutcomeType())
                        .findAny();
                if (eventResultOptional.isPresent()) {
                    EventResult changedEventResult = new EventResult(
                            databaseEventResult.getId(),
                            eventId,
                            eventResultOptional.get().getEventOutcomeType(),
                            eventResultOptional.get().getResultStatus());
                    int changedEventResultId = changedEventResult.getId();

                    ResultStatus oldResultStatus = databaseEventResult.getResultStatus();
                    ResultStatus newResultStatus = changedEventResult.getResultStatus();

                    if (oldResultStatus != newResultStatus) {
                        if (oldResultStatus == ResultStatus.DISABLED
                                || oldResultStatus == ResultStatus.BLOCKED
                                || oldResultStatus == ResultStatus.UNBLOCKED) {

                            if (newResultStatus == ResultStatus.DISABLED) {
                                refundBets(compositeDao, changedEventResultId);
                            } else if (newResultStatus == ResultStatus.FIRST_UPSHOT) {
                                payOutBets(compositeDao, changedEventResultId, Upshot.FIRST_UPSHOT);
                            } else if (newResultStatus == ResultStatus.SECOND_UPSHOT) {
                                payOutBets(compositeDao, changedEventResultId, Upshot.SECOND_UPSHOT);
                            }
                        } else if (oldResultStatus == ResultStatus.FIRST_UPSHOT) {
                            // забрать выигрыш, если newStatus - disabled - вернуть ставку, если newStatus-  secondUpshot, перерасчитать
                        } else if (oldResultStatus == ResultStatus.SECOND_UPSHOT) {
                            // забрать выигрыш, если newStatus - disabled - вернуть ставку, если newStatus - firstUpshot, перерасчитать
                        }
                        eventResultDao.updateEventResult(changedEventResult);
                    }
                } else {
                    // if upshot 1/2 money back
                    int eventResultToDeleteId = databaseEventResult.getId();
                    refundBets(compositeDao, eventResultToDeleteId);
                    eventResultDao.deleteEventResult(eventResultToDeleteId);
                }

            }
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                throw new DaoException(sqlException);
            }
        }
    }

    public void deleteEvent(int eventId) throws DaoException {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try (connection) {
            connection.setAutoCommit(false);

            CompositeDao compositeDao = new CompositeDao(connection);
            final EventDao eventDao = compositeDao.getEventDao();
            final EventResultDao eventResultDao = compositeDao.getEventResultDao();
            final AccountDao accountDao = compositeDao.getAccountDao();
            final BetDao betDao = compositeDao.getBetDao();

            List<EventResult> eventResults = eventResultDao.findAllByEventId(eventId);
            for (EventResult eventResult : eventResults) {
                ResultStatus resultStatus = eventResult.getResultStatus();
                if (resultStatus != ResultStatus.FIRST_UPSHOT
                        && resultStatus != ResultStatus.SECOND_UPSHOT) {

                    int eventResultId = eventResult.getId();
                    refundBets(compositeDao, eventResultId);
                }
            }
            eventDao.deleteEvent(eventId);
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                throw new DaoException(sqlException);
            }
        }
    }

    private void refundBets(CompositeDao compositeDao, int eventResultId) throws DaoException {
        final AccountDao accountDao = compositeDao.getAccountDao();
        final BetDao betDao = compositeDao.getBetDao();

        List<Bet> eventBets = betDao.findAllBetsByEventResultId(eventResultId);
        for (Bet bet : eventBets) {
            int accountId = bet.getAccountId();
            int betId = bet.getId();
            BigDecimal amount = bet.getAmount();
            accountDao.addToAccountBalance(accountId, amount);
            betDao.deleteBet(betId);
        }
    }

    private void payOutBets(CompositeDao compositeDao, int eventResultId, Upshot upshot) throws DaoException {
        final AccountDao accountDao = compositeDao.getAccountDao();
        final BetDao betDao = compositeDao.getBetDao();
        final EventDao eventDao = compositeDao.getEventDao();
        final EventResultDao eventResultDao = compositeDao.getEventResultDao();

        Optional<EventResult> eventResultOptional = eventResultDao.findEventResultById(eventResultId);
        if(eventResultOptional.isPresent()) {
            EventResult eventResult = eventResultOptional.get();
            int eventId = eventResult.getEventId();

            BigDecimal eventRoyalty = eventDao.findRoyaltyByEventId(eventId).get();
            BigDecimal totalUpshotBetsAmount = betDao.getTotalAmountOfBetsForUpshot(eventResultId, upshot);
            BigDecimal totalBetsAmount = betDao.getTotalAmountOfBets(eventResultId);

            BigDecimal upshotOdds = CoefficientCalculator.calculateOdds(totalBetsAmount, totalUpshotBetsAmount, eventRoyalty);

            List<Bet> betsOnEventResult = betDao.findAllBetsByEventResultId(eventResultId);
            for(Bet bet : betsOnEventResult) {
                if(bet.getUpshot() == upshot) {
                    int accountId = bet.getAccountId();
                    BigDecimal betAmount = bet.getAmount();
                    BigDecimal prize = betAmount.multiply(upshotOdds);
                    accountDao.addToAccountBalance(accountId, prize);
                }
            }
        }
    }

    static class CompositeDao {
        private final EventDao eventDao;
        private final EventResultDao eventResultDao;
        private final AccountDao accountDao;
        private final BetDao betDao;

        public CompositeDao(Connection transactionConnection) {
            this.eventDao = new EventDaoImpl(transactionConnection);
            this.eventResultDao = new EventResultDaoImpl(transactionConnection);
            this.accountDao = new AccountDaoImpl(transactionConnection);
            this.betDao = new BetDaoImpl(transactionConnection);
        }

        public EventDao getEventDao() {
            return eventDao;
        }

        public EventResultDao getEventResultDao() {
            return eventResultDao;
        }

        public AccountDao getAccountDao() {
            return accountDao;
        }

        public BetDao getBetDao() {
            return betDao;
        }
    }
}
