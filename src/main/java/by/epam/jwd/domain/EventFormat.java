package by.epam.jwd.domain;

public enum EventFormat {
    CSGO_BO1(1),
    CSGO_BO2(2),
    CSGO_BO3(3),
    CSGO_BO5(4),

    DOTA2_BO1(5),
    DOTA2_BO2(6),
    DOTA2_BO3(7),
    DOTA2_BO5(8);


    private final int id;

    EventFormat(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public EventFormat getEventFormatById(int id) {
        for(EventFormat eventFormat : values()) {
            if(eventFormat.getId() == id) {
                return eventFormat;
            }
        }
        throw new IllegalArgumentException("Unable to find EventFormat with id = " + id);
    }
}
