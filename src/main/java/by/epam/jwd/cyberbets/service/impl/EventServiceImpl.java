package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.EventDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.dto.EventDto;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class EventServiceImpl implements EventService {
    private EventDao eventDao = DaoProvider.INSTANCE.getEventDao();

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
    public Optional<Event> findEventById(int eventId) throws ServiceException {
        try {
            return eventDao.findEventById(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int createEvent(EventDto eventDto) throws ServiceException {
        try {
            return eventDao.createEvent(eventDto);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateEvent(Event event) throws ServiceException {
        try {
            eventDao.updateEvent(event); ;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteEvent(int eventId) throws ServiceException {
        try {
            eventDao.deleteEvent(eventId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
