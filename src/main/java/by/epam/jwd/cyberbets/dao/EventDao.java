package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.dto.EventDto;

import java.util.List;
import java.util.Optional;

public interface EventDao {
    List<Event> findAll() throws DaoException;
    Optional<Event> findEventById(int eventId) throws DaoException;
    int createEvent(EventDto eventDto) throws DaoException;
    void updateEvent(Event event) throws DaoException;
    void deleteEvent(int eventId) throws DaoException;
}
