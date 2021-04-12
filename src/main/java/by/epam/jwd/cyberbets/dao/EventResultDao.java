package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.EventResult;

import java.util.List;
import java.util.Optional;


public interface EventResultDao {
    List<EventResult> findAll() throws DaoException;
    List<EventResult> findAllByEventId(int eventId) throws DaoException;
    List<Integer> findAllEventResultIdsByEventId(int eventId) throws DaoException;
    Optional<EventResult> findEventResultById(int eventResultId) throws DaoException;
    int createEventResult(EventResult eventResult) throws DaoException;
    void updateEventResult(EventResult eventResult) throws DaoException;
    void deleteEventResult(int eventResultId) throws DaoException;
}
