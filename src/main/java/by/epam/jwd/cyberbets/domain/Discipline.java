package by.epam.jwd.cyberbets.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

public enum Discipline {
    @SerializedName("1") CSGO(1),
    @SerializedName("2") DOTA2(2),
    @SerializedName("3") LOL(3),
    @SerializedName("4") VALORANT(4);

    private final int id;

    Discipline(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<Discipline> getDisciplineById(int id) {
        Optional<Discipline> disciplineOptional = Optional.empty();
        for(Discipline discipline : values()) {
            if(discipline.getId() == id) {
                disciplineOptional = Optional.of(discipline);
                return disciplineOptional;
            }
        }
        return disciplineOptional;
    }
}
