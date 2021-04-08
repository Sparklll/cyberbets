package by.epam.jwd.cyberbets.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

public enum EventStatus {
    @SerializedName("1") PENDING(1),
    @SerializedName("2") LIVE(2),
    @SerializedName("3") FINISHED(3),
    @SerializedName("4") CANCELED(4);

    private final int id;

    EventStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<EventStatus> getEventStatusById(int id) {
        Optional<EventStatus> eventStatusOptional = Optional.empty();
        for (EventStatus eventStatus : values()) {
            if (eventStatus.getId() == id) {
                eventStatusOptional = Optional.of(eventStatus);
                return eventStatusOptional;
            }
        }
        return eventStatusOptional;
    }
}
