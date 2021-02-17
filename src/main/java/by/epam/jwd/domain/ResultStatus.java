package by.epam.jwd.domain;

public enum ResultStatus {
    UNBLOCKED(1),
    BLOCKED(2),
    CANCELED(3),
    SUCCESS(4),
    FAILURE(5);

    private final int id;

    ResultStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ResultStatus getResultStatusById(int id) {
        for(ResultStatus resultStatus : values()) {
            if(resultStatus.getId() == id) {
                return resultStatus;
            }
        }
        throw new IllegalArgumentException("Unable to find ResultStatus with id = " + id);
    }
}
