package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.EventDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.dao.impl.transactional.EventManager;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.EventStatus;
import by.epam.jwd.cyberbets.domain.dto.EventDto;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class EventServiceImpl implements EventService {
    private final EventDao eventDao = DaoProvider.INSTANCE.getEventDao();
    private final EventManager eventManager = DaoProvider.INSTANCE.getEventManager();

    EventServiceImpl() {

    }

    @Override
    public List<Event> findAll() throws ServiceException {
        try {
            return eventDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Event> findAllEventsByStatus(EventStatus eventStatus) throws ServiceException {
        try {
            return eventDao.findAllEventsByStatus(eventStatus);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Event> findAllEventsByStatus(EventStatus eventStatus, int limit) throws ServiceException {
        try {
            return eventDao.findAllEventsByStatus(eventStatus, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Integer> findAllEventIdsByStatusAndPeriod(EventStatus eventStatus, int daysNumber) throws ServiceException {
        try {
            return eventDao.findAllEventIdsByStatusAndPeriod(eventStatus, daysNumber);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Event> findEventById(int eventId) throws ServiceException {
        try {
            return eventDao.findEventById(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<BigDecimal> findRoyaltyByEventId(int eventId) throws ServiceException {
        try {
            return eventDao.findRoyaltyByEventId(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int createEvent(EventDto eventDto, List<EventResult> eventResults) throws ServiceException {
        try {
            return eventManager.createEvent(eventDto, eventResults);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateEvent(EventDto eventDto, List<EventResult> eventResults) throws ServiceException {
        try {
            eventManager.updateEvent(eventDto, eventResults);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateEventStatus(int eventId, EventStatus newStatus) throws ServiceException {
        try {
            eventDao.updateEventStatus(eventId, newStatus.getId());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteEvent(int eventId) throws ServiceException {
        try {
            eventManager.deleteEvent(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
