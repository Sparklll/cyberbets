package by.epam.jwd.cyberbets.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Bet extends Entity {
    private static final long serialVersionUID = -848437283670389073L;

    private int accountId;
    private EventResult eventResult;
    private BigDecimal amount;
    private Instant date;

    public Bet(int id, int accountId, EventResult eventResult, BigDecimal amount, Instant date) {
        super(id);
        this.accountId = accountId;
        this.eventResult = eventResult;
        this.amount = amount;
        this.date = date;
    }

    public Bet(int accountId, EventResult eventResult, BigDecimal amount, Instant date) {
        this.accountId = accountId;
        this.eventResult = eventResult;
        this.amount = amount;
        this.date = date;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public EventResult getEventResult() {
        return eventResult;
    }

    public void setEventResult(EventResult eventResult) {
        this.eventResult = eventResult;
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
                && Objects.equals(eventResult, bet.eventResult)
                && Objects.equals(amount, bet.amount)
                && Objects.equals(date, bet.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, eventResult, amount, date);
    }

    @Override
    public String toString() {
        return "Bet{" +
                "accountId=" + accountId +
                ", eventResult=" + eventResult +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
