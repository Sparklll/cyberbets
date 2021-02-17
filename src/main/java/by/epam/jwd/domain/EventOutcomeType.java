package by.epam.jwd.domain;

public enum EventOutcomeType {
    CSGO_FIRST_TEAM_TOTAL_WINNER(1),
    CSGO_SECOND_TEAM_TOTAL_WINNER(2),

    DOTA2_FIRST_TEAM_TOTAL_WINNER(3),
    DOTA2_SECOND_TEAM_TOTAL_WINNER(4);

    private final int id;

    EventOutcomeType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public EventOutcomeType getEventOutcomeTypeById(int id) {
        for(EventOutcomeType eventOutcomeType : values()) {
            if(eventOutcomeType.getId() == id) {
                return eventOutcomeType;
            }
        }
        throw new IllegalArgumentException("Unable to find EventOutcomeType with id = " + id);
    }
}
