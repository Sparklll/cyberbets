package by.epam.jwd.domain;

public enum EventOutcomeType {
    // GENERAL
    FIRST_TEAM_TOTAL_WINNER(1),
    SECOND_TEAM_TOTAL_WINNER(2),
    FIRST_TEAM_1MAP_WINNER(3),
    SECOND_TEAM_1MAP_WINNER(4),
    FIRST_TEAM_2MAP_WINNER(5),
    SECOND_TEAM_2MAP_WINNER(6),
    FIRST_TEAM_3MAP_WINNER(7),
    SECOND_TEAM_3MAP_WINNER(8),
    FIRST_TEAM_4MAP_WINNER(9),
    SECOND_TEAM_4MAP_WINNER(10),
    FIRST_TEAM_5MAP_WINNER(11),
    SECOND_TEAM_5MAP_WINNER(12);

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

    public EventOutcomeType getEventOutcomeTypeById(int id) {
        for(EventOutcomeType eventOutcomeType : values()) {
            if(eventOutcomeType.getId() == id) {
                return eventOutcomeType;
            }
        }
        throw new IllegalArgumentException("Unable to find EventOutcomeType with id = " + id);
    }
}
