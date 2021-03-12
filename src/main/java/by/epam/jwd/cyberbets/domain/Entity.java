package by.epam.jwd.cyberbets.domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private final int id;

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
