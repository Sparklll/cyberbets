package by.epam.jwd.cyberbets.domain;

import java.util.Optional;

public enum EventOutcomeType {
    // GENERAL
    TOTAL_WINNER(1),
    MAP1_WINNER(2),
    MAP2_WINNER(3),
    MAP3_WINNER(4),
    MAP4_WINNER(5),
    MAP5_WINNER(6);


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

    public Optional<EventOutcomeType> getEventOutcomeTypeById(int id) {
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
