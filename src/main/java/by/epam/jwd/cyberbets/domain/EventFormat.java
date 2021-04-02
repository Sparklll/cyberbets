package by.epam.jwd.cyberbets.domain;

import java.util.Optional;

public enum EventFormat {
    // GENERAL
    BO1(1),
    BO2(2),
    BO3(3),
    BO5(4);

    private final int id;

    EventFormat(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Optional<EventFormat> getEventFormatById(int id) {
        Optional<EventFormat> eventFormatOptional = Optional.empty();
        for(EventFormat eventFormat : values()) {
            if(eventFormat.getId() == id) {
                eventFormatOptional = Optional.of(eventFormat);
                return eventFormatOptional;
            }
        }
        return eventFormatOptional;
    }
}
