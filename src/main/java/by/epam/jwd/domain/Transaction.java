package by.epam.jwd.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Transaction extends Entity {
    private static final long serialVersionUID = -1295554811823666533L;

    private int transactionTypeId;
    private int accountId;
    private BigDecimal amount;
    private Instant date;

    public Transaction(int id, int transactionTypeId, int accountId, BigDecimal amount, Instant date) {
        super(id);
        this.transactionTypeId = transactionTypeId;
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
    }

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
        Transaction that = (Transaction) o;
        return transactionTypeId == that.transactionTypeId
                && accountId == that.accountId
                && Objects.equals(amount, that.amount)
                && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionTypeId, accountId, amount, date);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionTypeId=" + transactionTypeId +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
