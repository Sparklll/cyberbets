package by.epam.jwd.domain;

public enum Role {
    USER(1),
    ADMIN(2);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Role getRoleById(int id) {
        for(Role role : values()) {
            if(role.getId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unable to find Role with id = " + id);
    }
}
