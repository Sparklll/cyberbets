package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.dto.EventDto;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> findAll() throws ServiceException;
    Optional<Event> findEventById(int eventId) throws ServiceException;
    int createEvent(EventDto eventDto) throws ServiceException;
    void updateEvent(Event event) throws ServiceException;
    void deleteEvent(int eventId) throws ServiceException;
}
