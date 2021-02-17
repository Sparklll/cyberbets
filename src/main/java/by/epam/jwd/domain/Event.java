package by.epam.jwd.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Event extends Entity {
    private static final long serialVersionUID = -2403733588962027773L;

    private int disciplineId;
    private int eventFormatId;
    private int firstTeamId;
    private int secondTeamId;
    private Instant startDate;
    private BigDecimal royaltyPercentage;

    public Event(int id, int disciplineId, int eventFormatId, int firstTeamId, int secondTeamId, Instant startDate, BigDecimal royaltyPercentage) {
        super(id);
        this.disciplineId = disciplineId;
        this.eventFormatId = eventFormatId;
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;
        this.startDate = startDate;
        this.royaltyPercentage = royaltyPercentage;
    }

    public int getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(int disciplineId) {
        this.disciplineId = disciplineId;
    }

    public int getEventFormatId() {
        return eventFormatId;
    }

    public void setEventFormatId(int eventFormatId) {
        this.eventFormatId = eventFormatId;
    }

    public int getFirstTeamId() {
        return firstTeamId;
    }

    public void setFirstTeamId(int firstTeamId) {
        this.firstTeamId = firstTeamId;
    }

    public int getSecondTeamId() {
        return secondTeamId;
    }

    public void setSecondTeamId(int secondTeamId) {
        this.secondTeamId = secondTeamId;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getRoyaltyPercentage() {
        return royaltyPercentage;
    }

    public void setRoyaltyPercentage(BigDecimal royaltyPercentage) {
        this.royaltyPercentage = royaltyPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return disciplineId == event.disciplineId
                && eventFormatId == event.eventFormatId
                && firstTeamId == event.firstTeamId
                && secondTeamId == event.secondTeamId
                && Objects.equals(startDate, event.startDate)
                && Objects.equals(royaltyPercentage, event.royaltyPercentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disciplineId, eventFormatId, firstTeamId, secondTeamId, startDate, royaltyPercentage);
    }

    @Override
    public String toString() {
        return "Event{" +
                "disciplineId=" + disciplineId +
                ", eventFormatId=" + eventFormatId +
                ", firstTeamId=" + firstTeamId +
                ", secondTeamId=" + secondTeamId +
                ", startDate=" + startDate +
                ", royaltyPercentage=" + royaltyPercentage +
                '}';
    }
}
