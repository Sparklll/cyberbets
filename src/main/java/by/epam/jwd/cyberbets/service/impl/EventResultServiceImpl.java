package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.EventResultDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.dao.impl.EventResultDaoImpl;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.service.EventResultService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class EventResultServiceImpl implements EventResultService {
    private final EventResultDao eventResultDao = DaoProvider.INSTANCE.getEventResultDao();

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
