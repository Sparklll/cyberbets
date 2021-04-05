package by.epam.jwd.cyberbets.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

public enum TransactionType {
    @SerializedName("1") DEPOSIT(1),
    @SerializedName("2") WITHDRAW(2);

    private final int id;

    TransactionType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<TransactionType> getTransactionTypeById(int id) {
        Optional<TransactionType> transactionTypeOptional = Optional.empty();
        for (TransactionType transactionType : values()) {
            if (transactionType.getId() == id) {
                transactionTypeOptional = Optional.of(transactionType);
                return transactionTypeOptional;
            }
        }
        return transactionTypeOptional;
    }
}
