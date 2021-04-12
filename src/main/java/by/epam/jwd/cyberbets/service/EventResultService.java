package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.dto.EventResultCoefficientsDto;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface EventResultService {
    List<EventResult> findAll() throws ServiceException;
    List<EventResult> findAllByEventId(int eventId) throws ServiceException;
    Optional<EventResult> findEventResultById(int eventResultId) throws ServiceException;
    Optional<EventResultCoefficientsDto> receiveEventResultCoefficients(int eventResultId) throws ServiceException;
    int createEventResult(EventResult eventResult) throws ServiceException;
    void updateEventResult(EventResult eventResult) throws ServiceException;
    void deleteEventResult(int eventResultId) throws ServiceException;
}
