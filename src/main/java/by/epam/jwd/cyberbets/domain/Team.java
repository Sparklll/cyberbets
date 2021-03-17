package by.epam.jwd.cyberbets.domain;

import java.util.Objects;

public class Team extends Entity {
    private static final long serialVersionUID = 1344320937736154687L;

    private int disciplineId;
    private int logoResourceId;
    private int rating;
    private String name;

    public Team(int id, int disciplineId, int logoResourceId, int rating, String name) {
        super(id);
        this.disciplineId = disciplineId;
        this.logoResourceId = logoResourceId;
        this.rating = rating;
        this.name = name;
    }

    public int getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(int disciplineId) {
        this.disciplineId = disciplineId;
    }

    public int getLogoResourceId() {
        return logoResourceId;
    }

    public void setLogoResourceId(int logoResourceId) {
        this.logoResourceId = logoResourceId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return disciplineId == team.disciplineId
                && logoResourceId == team.logoResourceId
                && rating == team.rating
                && Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disciplineId, logoResourceId, rating, name);
    }

    @Override
    public String toString() {
        return "Team{" +
                "disciplineId=" + disciplineId +
                ", logoResourceId=" + logoResourceId +
                ", rating=" + rating +
                ", name='" + name + '\'' +
                '}';
    }
}
