package by.epam.jwd.cyberbets.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Transaction extends Entity {
    private static final long serialVersionUID = -1295554811823666533L;

    private int accountId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private Instant date;

    public Transaction(int id, int accountId, TransactionType transactionType, BigDecimal amount, Instant date) {
        super(id);
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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
        return accountId == that.accountId
                && transactionType == that.transactionType
                && Objects.equals(amount, that.amount)
                && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, transactionType, amount, date);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountId=" + accountId +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
