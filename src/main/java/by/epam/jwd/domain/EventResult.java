package by.epam.jwd.domain;

import java.util.Objects;

public class EventResult extends Entity {
    private static final long serialVersionUID = -1593407726732438884L;

    private int eventId;
    private int outcomeTypeId;
    private int resultStatusId;

    public EventResult(int id, int eventId, int outcomeTypeId, int resultStatusId) {
        super(id);
        this.eventId = eventId;
        this.outcomeTypeId = outcomeTypeId;
        this.resultStatusId = resultStatusId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getOutcomeTypeId() {
        return outcomeTypeId;
    }

    public void setOutcomeTypeId(int outcomeTypeId) {
        this.outcomeTypeId = outcomeTypeId;
    }

    public int getResultStatusId() {
        return resultStatusId;
    }

    public void setResultStatusId(int resultStatusId) {
        this.resultStatusId = resultStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventResult that = (EventResult) o;
        return eventId == that.eventId
                && outcomeTypeId == that.outcomeTypeId
                && resultStatusId == that.resultStatusId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, outcomeTypeId, resultStatusId);
    }

    @Override
    public String toString() {
        return "EventResult{" +
                "eventId=" + eventId +
                ", outcomeTypeId=" + outcomeTypeId +
                ", resultStatusId=" + resultStatusId +
                '}';
    }
}
