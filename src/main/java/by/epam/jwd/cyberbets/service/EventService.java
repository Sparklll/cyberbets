package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.dto.EventDto;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> findAll() throws ServiceException;
    Optional<Event> findEventById(int eventId) throws ServiceException;
    int createEvent(EventDto eventDto, List<EventResult> eventResults) throws ServiceException;
    void updateEvent(EventDto eventDto, List<EventResult> eventResults) throws ServiceException;
    void deleteEvent(int eventId) throws ServiceException;
}
