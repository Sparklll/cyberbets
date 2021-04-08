package by.epam.jwd.cyberbets.controller.validator;

import by.epam.jwd.cyberbets.domain.Discipline;
import by.epam.jwd.cyberbets.domain.EventFormat;
import by.epam.jwd.cyberbets.domain.EventStatus;
import by.epam.jwd.cyberbets.domain.dto.EventDto;

import java.math.BigDecimal;

public class EventValidator implements Validator<EventDto> {
    @Override
    public boolean isValid(EventDto eventDto) {
        return  isDisciplineIdValid(eventDto.disciplineId())
                && isStatusIdValid(eventDto.eventStatusId())
                && isFormatIdValid(eventDto.formatId())
                && isRoyaltyValid(eventDto.royalty());
    }

    private boolean isDisciplineIdValid(int disciplineId) {
        return Discipline.getDisciplineById(disciplineId).isPresent();
    }

    private boolean isFormatIdValid(int formatId) {
        return EventFormat.getEventFormatById(formatId).isPresent();
    }

    private boolean isStatusIdValid(int statusId) {
        return EventStatus.getEventStatusById(statusId).isPresent();
    }

    private boolean isRoyaltyValid(BigDecimal royalty) {
        return royalty.doubleValue() >=0 && royalty.doubleValue() <= 100;
    }
}
