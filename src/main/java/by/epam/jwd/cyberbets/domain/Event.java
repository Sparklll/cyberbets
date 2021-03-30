package by.epam.jwd.cyberbets.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Event extends Entity {
    private static final long serialVersionUID = -2403733588962027773L;

    private Discipline discipline;
    private EventFormat eventFormat;
    private League league;
    private Team firstTeam;
    private Team secondTeam;
    private Instant startDate;
    private BigDecimal royaltyPercentage;

    public Event(int id, Discipline discipline, EventFormat eventFormat, League league, Team firstTeam, Team secondTeam, Instant startDate, BigDecimal royaltyPercentage) {
        super(id);
        this.discipline = discipline;
        this.eventFormat = eventFormat;
        this.league = league;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.startDate = startDate;
        this.royaltyPercentage = royaltyPercentage;
    }

    public Event(Discipline discipline, EventFormat eventFormat, League league, Team firstTeam, Team secondTeam, Instant startDate, BigDecimal royaltyPercentage) {
        this.discipline = discipline;
        this.eventFormat = eventFormat;
        this.league = league;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.startDate = startDate;
        this.royaltyPercentage = royaltyPercentage;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return discipline == event.discipline
                && eventFormat == event.eventFormat
                && Objects.equals(league, event.league)
                && Objects.equals(firstTeam, event.firstTeam)
                && Objects.equals(secondTeam, event.secondTeam)
                && Objects.equals(startDate, event.startDate)
                && Objects.equals(royaltyPercentage, event.royaltyPercentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discipline, eventFormat, league, firstTeam, secondTeam, startDate, royaltyPercentage);
    }

    @Override
    public String toString() {
        return "Event{" +
                "discipline=" + discipline +
                ", eventFormat=" + eventFormat +
                ", league=" + league +
                ", firstTeam=" + firstTeam +
                ", secondTeam=" + secondTeam +
                ", startDate=" + startDate +
                ", royaltyPercentage=" + royaltyPercentage +
                '}';
    }
}
