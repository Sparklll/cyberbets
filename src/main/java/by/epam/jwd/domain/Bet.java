package by.epam.jwd.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Bet extends Entity {
    private static final long serialVersionUID = -848437283670389073L;


    private int accountId;
    private int eventResultId;
    private BigDecimal amount;
    private Instant date;

    public Bet(int id, int accountId, int eventResultId, BigDecimal amount, Instant date) {
        super(id);
        this.accountId = accountId;
        this.eventResultId = eventResultId;
        this.amount = amount;
        this.date = date;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getEventResultId() {
        return eventResultId;
    }

    public void setEventResultId(int eventResultId) {
        this.eventResultId = eventResultId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return accountId == bet.accountId
                && eventResultId == bet.eventResultId
                && Objects.equals(amount, bet.amount)
                && Objects.equals(date, bet.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, eventResultId, amount, date);
    }

    @Override
    public String toString() {
        return "Bet{" +
                "accountId=" + accountId +
                ", eventResultId=" + eventResultId +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
