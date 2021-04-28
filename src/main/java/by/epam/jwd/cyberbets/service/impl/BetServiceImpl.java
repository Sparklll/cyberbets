package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.BetDao;
import by.epam.jwd.cyberbets.dao.EventDao;
import by.epam.jwd.cyberbets.dao.EventResultDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.dao.impl.transactional.BetManager;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.EventStatus;
import by.epam.jwd.cyberbets.domain.dto.BetDto;
import by.epam.jwd.cyberbets.service.BetService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.job.LoadEventJob;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class BetServiceImpl implements BetService {
    private final BetDao betDao = DaoProvider.INSTANCE.getBetDao();
    private final EventDao eventDao = DaoProvider.INSTANCE.getEventDao();
    private final EventResultDao eventResultDao = DaoProvider.INSTANCE.getEventResultDao();
    private final BetManager betManager = DaoProvider.INSTANCE.getBetManager();

    BetServiceImpl() {
    }

    @Override
    public List<Bet> findAllBetsByAccountId(int accountId) throws ServiceException {
        try {
            return betDao.findAllBetsByAccountId(accountId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bet> findAllBetsByEventId(int eventId) throws ServiceException {
        try {
            return betDao.findAllBetsByEventId(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bet> findAllBetsByAccountIdAndEventId(int accountId, int eventId) throws ServiceException {
        try {
            return betDao.findAllBetsByAccountIdAndEventId(accountId, eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bet> findAllBetsByEventResultId(int eventResultId) throws ServiceException {
        try {
            return betDao.findAllBetsByEventResultId(eventResultId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Bet> findBetById(int betId) throws ServiceException {
        try {
            return betDao.findBetById(betId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BigDecimal getTotalAmountOfBets(int eventResultId) throws ServiceException {
        try {
            return betDao.getTotalAmountOfBets(eventResultId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BigDecimal getTotalAmountOfBetsForUpshot(int eventResultId, Bet.Upshot upshot) throws ServiceException {
        try {
            return betDao.getTotalAmountOfBetsForUpshot(eventResultId, upshot);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BigDecimal getTotalProfitByPeriod(int daysNumber) throws ServiceException {
        try {
            BigDecimal totalProfit = BigDecimal.ZERO;
            List<Integer> pastEventIds = eventDao.findAllEventIdsByStatusAndPeriod(EventStatus.FINISHED, daysNumber);
            for (int eventId : pastEventIds) {
                Optional<BigDecimal> eventRoyaltyOptional = eventDao.findRoyaltyByEventId(eventId);
                if (eventRoyaltyOptional.isPresent()) {
                    BigDecimal eventRoyalty = eventRoyaltyOptional.get().scaleByPowerOfTen(-2);

                    List<Integer> eventResultIds = eventResultDao.findAllEventResultIdsByEventId(eventId);
                    for (int eventResultId : eventResultIds) {
                        BigDecimal eventResultProfit = getTotalAmountOfBets(eventResultId).multiply(eventRoyalty);
                        totalProfit = totalProfit.add(eventResultProfit);
                    }
                }
            }
            return totalProfit;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public BigDecimal getTotalAmountOfBetsOnActiveEvents() throws ServiceException {
        try {
            BigDecimal activeEventsBetsAmount = BigDecimal.ZERO;
            List<Event> currentEvents = Stream.concat(LoadEventJob.cachedLiveEvents.stream(),
                    LoadEventJob.cachedUpcomingEvents.stream()).toList();

            for (Event event: currentEvents) {
                int eventId = event.getId();
                List<Integer> eventResultIds = eventResultDao.findAllEventResultIdsByEventId(eventId);
                for (int eventResultId : eventResultIds) {
                    BigDecimal eventResultBetsAmount = getTotalAmountOfBets(eventResultId);
                    activeEventsBetsAmount = activeEventsBetsAmount.add(eventResultBetsAmount);
                }
            }
            return activeEventsBetsAmount;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void placeBet(BetDto betDto) throws ServiceException {
        try {
            betManager.createBet(betDto);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateBet(BetDto betDto) throws ServiceException {
        try {
            betManager.updateBet(betDto);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBet(BetDto betDto) throws ServiceException {
        try {
            betManager.deleteBet(betDto);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
