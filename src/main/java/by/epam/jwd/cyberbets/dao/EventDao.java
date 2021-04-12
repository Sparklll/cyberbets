package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventStatus;
import by.epam.jwd.cyberbets.domain.dto.EventDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EventDao {
    List<Event> findAll() throws DaoException;
    List<Event> findAllEventsByStatus(EventStatus eventStatus) throws DaoException;
    List<Event> findAllEventsByStatus(EventStatus eventStatus, int limit) throws DaoException;
    Optional<Event> findEventById(int eventId) throws DaoException;
    Optional<BigDecimal> findRoyaltyByEventId(int eventId) throws DaoException;
    int createEvent(EventDto eventDto) throws DaoException;
    void updateEvent(EventDto eventDto) throws DaoException;
    void deleteEvent(int eventId) throws DaoException;
}
