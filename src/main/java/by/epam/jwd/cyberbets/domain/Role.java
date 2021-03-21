package by.epam.jwd.cyberbets.domain;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public enum Role {
    GUEST(1, GUEST_ROLE),
    USER(2, USER_ROLE),
    ADMIN(3, ADMIN_ROLE);

    private final int id;
    private final String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Role getRoleById(int id) {
        for(Role role : values()) {
            if(role.getId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unable to find Role with id = " + id);
    }
}
