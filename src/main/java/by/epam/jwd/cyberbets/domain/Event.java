package by.epam.jwd.cyberbets.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Event extends Entity {
    private static final long serialVersionUID = -2403733588962027773L;

    private Discipline discipline;
    private League league;
    private Team firstTeam;
    private Team secondTeam;
    private EventFormat eventFormat;
    private Instant startDate;
    private BigDecimal royaltyPercentage;
    private EventStatus status;

    public Event(Discipline discipline, League league, Team firstTeam, Team secondTeam,
                 EventFormat eventFormat, Instant startDate, BigDecimal royaltyPercentage, EventStatus status) {
        this.discipline = discipline;
        this.league = league;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.eventFormat = eventFormat;
        this.startDate = startDate;
        this.royaltyPercentage = royaltyPercentage;
        this.status = status;
    }

    public Event(int id, Discipline discipline, League league, Team firstTeam, Team secondTeam,
                 EventFormat eventFormat, Instant startDate, BigDecimal royaltyPercentage, EventStatus status) {
        super(id);
        this.discipline = discipline;
        this.league = league;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.eventFormat = eventFormat;
        this.startDate = startDate;
        this.royaltyPercentage = royaltyPercentage;
        this.status = status;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public EventFormat getEventFormat() {
        return eventFormat;
    }

    public void setEventFormat(EventFormat eventFormat) {
        this.eventFormat = eventFormat;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
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

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return discipline == event.discipline
                && Objects.equals(league, event.league)
                && Objects.equals(firstTeam, event.firstTeam)
                && Objects.equals(secondTeam, event.secondTeam)
                && eventFormat == event.eventFormat
                && Objects.equals(startDate, event.startDate)
                && Objects.equals(royaltyPercentage, event.royaltyPercentage)
                && status == event.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(discipline, league, firstTeam, secondTeam, eventFormat, startDate, royaltyPercentage, status);
    }

    @Override
    public String toString() {
        return "Event{" +
                "discipline=" + discipline +
                ", league=" + league +
                ", firstTeam=" + firstTeam +
                ", secondTeam=" + secondTeam +
                ", eventFormat=" + eventFormat +
                ", startDate=" + startDate +
                ", royaltyPercentage=" + royaltyPercentage +
                ", status=" + status +
                '}';
    }
}
