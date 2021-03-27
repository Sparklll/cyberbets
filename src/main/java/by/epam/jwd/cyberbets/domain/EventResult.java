package by.epam.jwd.cyberbets.domain;

import java.util.Objects;

public class EventResult extends Entity {
    private static final long serialVersionUID = -1593407726732438884L;

    private int eventId;
    private EventOutcomeType eventOutcomeType;
    private ResultStatus resultStatus;

    public EventResult(int id, int eventId, EventOutcomeType eventOutcomeType, ResultStatus resultStatus) {
        super(id);
        this.eventId = eventId;
        this.eventOutcomeType = eventOutcomeType;
        this.resultStatus = resultStatus;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public EventOutcomeType getEventOutcomeType() {
        return eventOutcomeType;
    }

    public void setEventOutcomeType(EventOutcomeType eventOutcomeType) {
        this.eventOutcomeType = eventOutcomeType;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventResult that = (EventResult) o;
        return eventId == that.eventId
                && eventOutcomeType == that.eventOutcomeType
                && resultStatus == that.resultStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventOutcomeType, resultStatus);
    }

    @Override
    public String toString() {
        return "EventResult{" +
                "eventId=" + eventId +
                ", eventOutcomeType=" + eventOutcomeType +
                ", resultStatus=" + resultStatus +
                '}';
    }
}
