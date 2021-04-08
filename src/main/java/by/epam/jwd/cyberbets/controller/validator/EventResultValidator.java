package by.epam.jwd.cyberbets.controller.validator;

import by.epam.jwd.cyberbets.domain.EventOutcomeType;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.ResultStatus;

import java.util.List;

public class EventResultValidator implements Validator<EventResult> {
    @Override
    public boolean isValid(EventResult eventResult) {
        return isOutcomeTypeValid(eventResult.getEventOutcomeType().getId())
                && isResultStatusValid(eventResult.getResultStatus().getId());
    }

    public boolean isValid(List<EventResult> eventResults) {
        for (EventResult er : eventResults) {
            if(!isValid(er)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOutcomeTypeValid(int eventOutcomeTypeId) {
        return EventOutcomeType.getEventOutcomeTypeById(eventOutcomeTypeId).isPresent();
    }

    private boolean isResultStatusValid(int resultStatusId) {
        return ResultStatus.getResultStatusById(resultStatusId).isPresent();
    }
}
