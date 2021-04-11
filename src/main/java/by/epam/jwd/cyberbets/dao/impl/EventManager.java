package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.AccountDao;
import by.epam.jwd.cyberbets.dao.BetDao;
import by.epam.jwd.cyberbets.dao.EventDao;
import by.epam.jwd.cyberbets.dao.EventResultDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.ResultStatus;
import by.epam.jwd.cyberbets.domain.dto.EventDto;
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

            final EventDao eventDao = new EventDaoImpl(connection);
            final EventResultDao eventResultDao = new EventResultDaoImpl(connection);

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

            final EventDao eventDao = new EventDaoImpl(connection);
            final EventResultDao eventResultDao = new EventResultDaoImpl(connection);
            final AccountDao accountDao = new AccountDaoImpl(connection);
            final BetDao betDao = new BetDaoImpl(connection);

            int eventId = eventDto.eventId();

            eventDao.updateEvent(eventDto);
            List<EventResult> databaseEventResults = eventResultDao.findAllByEventId(eventId);
            for (EventResult eventResult : eventResults) {
                eventResult.setEventId(eventId);
            }

            // in case of change eventFormat /|:non-effective:|\
            List<EventResult> toAddEventResults = eventResults.stream()
                    .filter(er -> !databaseEventResults.stream()
                            .map(EventResult::getEventOutcomeType)
                            .collect(Collectors.toList())
                            .contains(er.getEventOutcomeType()))
                    .collect(Collectors.toList());

            for(EventResult toAddResult : toAddEventResults) {
                eventResultDao.createEventResult(toAddResult);
            }

            for (EventResult databaseEventResult : databaseEventResults) {
                Optional<EventResult> eventResultOptional = eventResults.stream()
                        .filter(er -> er.getEventOutcomeType() == databaseEventResult.getEventOutcomeType())
                        .findAny();
                if (eventResultOptional.isPresent()) {
                    EventResult changedEventResult = eventResultOptional.get();
                    changedEventResult.setEventId(eventId);

                    ResultStatus oldResultStatus = databaseEventResult.getResultStatus();
                    ResultStatus newResultStatus = changedEventResult.getResultStatus();

                    if (oldResultStatus != newResultStatus) {
                        if (oldResultStatus == ResultStatus.DISABLED
                                || oldResultStatus == ResultStatus.BLOCKED
                                || oldResultStatus == ResultStatus.UNBLOCKED) {

                        } else {


                        }
                        eventResultDao.updateEventResult(changedEventResult);
                    }
                } else {
                    // if upshot 1/2 money back
                    int eventResultToDeleteId = databaseEventResult.getId();
                    eventResultDao.deleteEventResult(eventResultToDeleteId);
                    refundBets(accountDao, betDao, eventResultToDeleteId);
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

            final EventDao eventDao = new EventDaoImpl(connection);
            final EventResultDao eventResultDao = new EventResultDaoImpl(connection);
            final AccountDao accountDao = new AccountDaoImpl(connection);
            final BetDao betDao = new BetDaoImpl(connection);

            List<EventResult> eventResults = eventResultDao.findAllByEventId(eventId);
            for (EventResult eventResult : eventResults) {
                ResultStatus resultStatus = eventResult.getResultStatus();
                if (resultStatus != ResultStatus.FIRST_UPSHOT
                        && resultStatus != ResultStatus.SECOND_UPSHOT) {

                    int eventResultId = eventResult.getId();
                    refundBets(accountDao, betDao, eventResultId);
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

    private void refundBets(AccountDao accountDao, BetDao betDao, int eventResultId) throws DaoException {
        List<Bet> eventBets = betDao.findAllBetsByEventResultId(eventResultId);
        for (Bet bet : eventBets) {
            int accountId = bet.getAccountId();
            BigDecimal amount = bet.getAmount();
            accountDao.addToAccountBalance(accountId, amount);
        }
    }
}
