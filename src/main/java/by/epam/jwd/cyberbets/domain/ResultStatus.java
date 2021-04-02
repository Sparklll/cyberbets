package by.epam.jwd.cyberbets.domain;

import java.util.Optional;

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

    public Optional<ResultStatus> getResultStatusById(int id) throws IllegalArgumentException{
        Optional<ResultStatus> resultStatusOptional = Optional.empty();
        for(ResultStatus resultStatus : values()) {
            if(resultStatus.getId() == id) {
                resultStatusOptional = Optional.of(resultStatus);
                return resultStatusOptional;
            }
        }
        return resultStatusOptional;
    }
}
