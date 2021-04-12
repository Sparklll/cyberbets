package by.epam.jwd.cyberbets.domain.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class CoefficientsDto {
    private final int eventResultId;
    private final int eventOutcomeTypeId;
    private final BigDecimal firstUpshotOdds;
    private final BigDecimal firstUpshotPercent;
    private final BigDecimal secondUpshotOdds;
    private final BigDecimal secondUpshotPercent;

    public CoefficientsDto(int eventResultId, int eventOutcomeTypeId, BigDecimal firstUpshotOdds,
                           BigDecimal firstUpshotPercent, BigDecimal secondUpshotOdds, BigDecimal secondUpshotPercent) {
        this.eventResultId = eventResultId;
        this.eventOutcomeTypeId = eventOutcomeTypeId;
        this.firstUpshotOdds = firstUpshotOdds;
        this.firstUpshotPercent = firstUpshotPercent;
        this.secondUpshotOdds = secondUpshotOdds;
        this.secondUpshotPercent = secondUpshotPercent;
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
                && Objects.equals(secondUpshotPercent, that.secondUpshotPercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventResultId, eventOutcomeTypeId, firstUpshotOdds, firstUpshotPercent, secondUpshotOdds, secondUpshotPercent);
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
                '}';
    }
}
