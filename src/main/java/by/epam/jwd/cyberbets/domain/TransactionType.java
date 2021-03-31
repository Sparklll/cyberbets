package by.epam.jwd.cyberbets.domain;

import java.util.Optional;

public enum TransactionType {
    DEPOSIT(1),
    WITHDRAW(2);

    private final int id;

    TransactionType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<TransactionType> getTransactionTypeById(int id) {
        Optional<TransactionType> transactionTypeOptional = Optional.empty();
        for(TransactionType transactionType : values()) {
            if(transactionType.getId() == id) {
                transactionTypeOptional = Optional.of(transactionType);
            }
        }
        return transactionTypeOptional;
    }
}
