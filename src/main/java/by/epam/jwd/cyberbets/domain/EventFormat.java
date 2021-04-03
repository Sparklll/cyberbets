package by.epam.jwd.cyberbets.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

public enum EventFormat {
    // GENERAL
    @SerializedName("1") BO1(1),
    @SerializedName("2") BO2(2),
    @SerializedName("3") BO3(3),
    @SerializedName("4") BO5(4);

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
