package by.epam.jwd.cyberbets.domain;

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

    public TransactionType getTransactionTypeById(int id) {
        for(TransactionType transactionType : values()) {
            if(transactionType.getId() == id) {
                return transactionType;
            }
        }
        throw new IllegalArgumentException("Unable to find TransactionType with id = " + id);
    }
}
