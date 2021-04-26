package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.BetDao;
import by.epam.jwd.cyberbets.dao.EventDao;
import by.epam.jwd.cyberbets.dao.EventResultDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.domain.Bet.Upshot;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.ResultStatus;
import by.epam.jwd.cyberbets.domain.dto.CoefficientsDto;
import by.epam.jwd.cyberbets.service.EventResultService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.util.CoefficientCalculator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class EventResultServiceImpl implements EventResultService {
    private final EventResultDao eventResultDao = DaoProvider.INSTANCE.getEventResultDao();
    private final BetDao betDao = DaoProvider.INSTANCE.getBetDao();
    private final EventDao eventDao = DaoProvider.INSTANCE.getEventDao();

    EventResultServiceImpl() {

    }

    @Override
    public List<EventResult> findAll() throws ServiceException {
        try {
            return eventResultDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<EventResult> findAllByEventId(int eventId) throws ServiceException {
        try {
            return eventResultDao.findAllByEventId(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<EventResult> findEventResultById(int eventResultId) throws ServiceException {
        try {
            return eventResultDao.findEventResultById(eventResultId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Integer> findAllEventResultIdsByEventId(int eventId) throws ServiceException {
        try {
            return eventResultDao.findAllEventResultIdsByEventId(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<CoefficientsDto> receiveEventResultCoefficients(int eventResultId) throws ServiceException {
        try {
            Optional<EventResult> eventResultOptional = findEventResultById(eventResultId);
            if (eventResultOptional.isPresent()) {
                EventResult eventResult = eventResultOptional.get();
                int eventId = eventResult.getEventId();
                int eventOutcomeTypeId = eventResult.getEventOutcomeType().getId();
                ResultStatus resultStatus = eventResult.getResultStatus();

                BigDecimal eventRoyalty = eventDao.findRoyaltyByEventId(eventId).get();
                BigDecimal totalBetsAmount = betDao.getTotalAmountOfBets(eventResultId);
                BigDecimal firstUpshotBetsAmount = betDao.getTotalAmountOfBetsForUpshot(eventResultId, Upshot.FIRST_UPSHOT);
                BigDecimal secondUpshotBetsAmount = betDao.getTotalAmountOfBetsForUpshot(eventResultId, Upshot.SECOND_UPSHOT);

                BigDecimal firstUpshotOdds = CoefficientCalculator.calculateOdds(totalBetsAmount, firstUpshotBetsAmount, eventRoyalty);
                BigDecimal secondUpshotOdds = CoefficientCalculator.calculateOdds(totalBetsAmount, secondUpshotBetsAmount, eventRoyalty);
                BigDecimal firstUpshotPercent = CoefficientCalculator.calculateUpshotPercent(totalBetsAmount, firstUpshotBetsAmount);
                BigDecimal secondUpshotPercent = CoefficientCalculator.calculateUpshotPercent(totalBetsAmount, secondUpshotBetsAmount);

                Upshot result = null;
                if(resultStatus == ResultStatus.FIRST_UPSHOT) {
                    result = Upshot.FIRST_UPSHOT;
                } else if (resultStatus == ResultStatus.SECOND_UPSHOT) {
                    result = Upshot.SECOND_UPSHOT;
                }

                return Optional.of(new CoefficientsDto(eventResultId, eventOutcomeTypeId, firstUpshotOdds,
                        firstUpshotPercent, secondUpshotOdds, secondUpshotPercent, result));
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int createEventResult(EventResult eventResult) throws ServiceException {
        try {
            return eventResultDao.createEventResult(eventResult);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateEventResult(EventResult eventResult) throws ServiceException {
        try {
            eventResultDao.updateEventResult(eventResult);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteEventResult(int eventResultId) throws ServiceException {
        try {
            eventResultDao.deleteEventResult(eventResultId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
