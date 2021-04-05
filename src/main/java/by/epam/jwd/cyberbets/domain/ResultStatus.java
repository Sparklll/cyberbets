package by.epam.jwd.cyberbets.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

public enum ResultStatus {
    @SerializedName("1") DISABLED(1),
    @SerializedName("2") UNBLOCKED(2),
    @SerializedName("3") BLOCKED(3),
    @SerializedName("4") SUCCESS(4),
    @SerializedName("5") FAILURE(5);

    private final int id;

    ResultStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Optional<ResultStatus> getResultStatusById(int id) throws IllegalArgumentException {
        Optional<ResultStatus> resultStatusOptional = Optional.empty();
        for (ResultStatus resultStatus : values()) {
            if (resultStatus.getId() == id) {
                resultStatusOptional = Optional.of(resultStatus);
                return resultStatusOptional;
            }
        }
        return resultStatusOptional;
    }
}
