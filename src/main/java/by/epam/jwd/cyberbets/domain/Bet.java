package by.epam.jwd.cyberbets.domain;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class Bet extends Entity {
    private static final long serialVersionUID = -848437283670389073L;

    private int accountId;
    private EventResult eventResult;
    private Upshot upshot;
    private BigDecimal amount;
    private Instant date;

    public Bet(int accountId, EventResult eventResult, Upshot upshot, BigDecimal amount, Instant date) {
        this.accountId = accountId;
        this.eventResult = eventResult;
        this.upshot = upshot;
        this.amount = amount;
        this.date = date;
    }

    public Bet(int id, int accountId, EventResult eventResult, Upshot upshot, BigDecimal amount, Instant date) {
        super(id);
        this.accountId = accountId;
        this.eventResult = eventResult;
        this.upshot = upshot;
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

    public Upshot getUpshot() {
        return upshot;
    }

    public void setUpshot(Upshot upshot) {
        this.upshot = upshot;
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
                && upshot == bet.upshot
                && Objects.equals(amount, bet.amount)
                && Objects.equals(date, bet.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, eventResult, upshot, amount, date);
    }

    @Override
    public String toString() {
        return "Bet{" +
                "accountId=" + accountId +
                ", eventResult=" + eventResult +
                ", upshot=" + upshot +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }

    public enum Upshot {
        @SerializedName("1") FIRST_UPSHOT(1),
        @SerializedName("2") SECOND_UPSHOT(2);

        private final int id;

        Upshot(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Optional<Upshot> getUpshotById(int id) {
            Optional<Upshot> upshotOptional = Optional.empty();
            for (Upshot upshot : values()) {
                if (upshot.getId() == id) {
                    upshotOptional = Optional.of(upshot);
                    return upshotOptional;
                }
            }
            return upshotOptional;
        }
    }
}
