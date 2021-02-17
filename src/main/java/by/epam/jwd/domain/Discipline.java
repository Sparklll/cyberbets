package by.epam.jwd.domain;

public enum Discipline {
    CSGO(1),
    DOTA2(2);

    private final int id;

    Discipline(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Discipline getDisciplineById(int id) {
        for(Discipline discipline : values()) {
            if(discipline.getId() == id) {
                return discipline;
            }
        }
        throw new IllegalArgumentException("Unable to find Discipline with id = " + id);
    }
}
