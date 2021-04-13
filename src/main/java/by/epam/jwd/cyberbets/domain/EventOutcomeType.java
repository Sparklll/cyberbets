package by.epam.jwd.cyberbets.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

public enum EventOutcomeType {
    // GENERAL
    @SerializedName("1") TOTAL_WINNER(1),
    @SerializedName("2") MAP1_WINNER(2),
    @SerializedName("3") MAP2_WINNER(3),
    @SerializedName("4") MAP3_WINNER(4),
    @SerializedName("5") MAP4_WINNER(5),
    @SerializedName("6") MAP5_WINNER(6);

    // SPECIFIC \\
    // CS:GO

    // DOTA2

    // LOL

    // VALORANT

    private final int id;

    EventOutcomeType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<EventOutcomeType> getEventOutcomeTypeById(int id) {
        Optional<EventOutcomeType> eventOutcomeTypeOptional = Optional.empty();
        for(EventOutcomeType eventOutcomeType : values()) {
            if(eventOutcomeType.getId() == id) {
                eventOutcomeTypeOptional = Optional.of(eventOutcomeType);
                return eventOutcomeTypeOptional;
            }
        }
        return eventOutcomeTypeOptional;
    }
}
