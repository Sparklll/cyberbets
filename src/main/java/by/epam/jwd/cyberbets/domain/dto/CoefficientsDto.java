package by.epam.jwd.cyberbets.domain.dto;

import by.epam.jwd.cyberbets.domain.Bet.Upshot;

import java.math.BigDecimal;
import java.util.Objects;

public class CoefficientsDto {
    private final int eventResultId;
    private final int eventOutcomeTypeId;
    private final BigDecimal firstUpshotOdds;
    private final BigDecimal firstUpshotPercent;
    private final BigDecimal secondUpshotOdds;
    private final BigDecimal secondUpshotPercent;
    private final Upshot result;

    public CoefficientsDto(int eventResultId, int eventOutcomeTypeId, BigDecimal firstUpshotOdds,
                           BigDecimal firstUpshotPercent, BigDecimal secondUpshotOdds, BigDecimal secondUpshotPercent, Upshot result) {
        this.eventResultId = eventResultId;
        this.eventOutcomeTypeId = eventOutcomeTypeId;
        this.firstUpshotOdds = firstUpshotOdds;
        this.firstUpshotPercent = firstUpshotPercent;
        this.secondUpshotOdds = secondUpshotOdds;
        this.secondUpshotPercent = secondUpshotPercent;
        this.result = result;
    }

    public int getEventResultId() {
        return eventResultId;
    }

    public int getEventOutcomeTypeId() {
        return eventOutcomeTypeId;
    }

    public BigDecimal getFirstUpshotOdds() {
        return firstUpshotOdds;
    }

    public BigDecimal getFirstUpshotPercent() {
        return firstUpshotPercent;
    }

    public BigDecimal getSecondUpshotOdds() {
        return secondUpshotOdds;
    }

    public BigDecimal getSecondUpshotPercent() {
        return secondUpshotPercent;
    }

    public Upshot getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoefficientsDto that = (CoefficientsDto) o;
        return eventResultId == that.eventResultId
                && eventOutcomeTypeId == that.eventOutcomeTypeId
                && Objects.equals(firstUpshotOdds, that.firstUpshotOdds)
                && Objects.equals(firstUpshotPercent, that.firstUpshotPercent)
                && Objects.equals(secondUpshotOdds, that.secondUpshotOdds)
                && Objects.equals(secondUpshotPercent, that.secondUpshotPercent)
                && result == that.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventResultId, eventOutcomeTypeId, firstUpshotOdds, firstUpshotPercent, secondUpshotOdds, secondUpshotPercent, result);
    }

    @Override
    public String toString() {
        return "CoefficientsDto{" +
                "eventResultId=" + eventResultId +
                ", eventOutcomeTypeId=" + eventOutcomeTypeId +
                ", firstUpshotOdds=" + firstUpshotOdds +
                ", firstUpshotPercent=" + firstUpshotPercent +
                ", secondUpshotOdds=" + secondUpshotOdds +
                ", secondUpshotPercent=" + secondUpshotPercent +
                ", result=" + result +
                '}';
    }
}
