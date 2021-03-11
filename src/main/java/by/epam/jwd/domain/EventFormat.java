package by.epam.jwd.domain;

public enum EventFormat {
    // GENERAL
    BO1(1),
    BO2(2),
    BO3(3),
    BO5(4);

    // CS:GO

    // DOTA2

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
