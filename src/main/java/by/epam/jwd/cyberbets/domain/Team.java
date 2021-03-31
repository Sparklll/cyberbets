package by.epam.jwd.cyberbets.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Team extends Entity {
    private static final long serialVersionUID = 1344320937736154687L;

    @SerializedName("teamName")
    private String name;
    @SerializedName("teamRating")
    private int rating;
    private Discipline discipline;
    @SerializedName("teamLogo")
    private Resource logoResource;


    public Team(int id, String name, int rating, Discipline discipline, Resource logoResource) {
        super(id);
        this.name = name;
        this.rating = rating;
        this.discipline = discipline;
        this.logoResource = logoResource;
    }

    public Team(String name, int rating, Discipline discipline, Resource logoResource) {
        this.name = name;
        this.rating = rating;
        this.discipline = discipline;
        this.logoResource = logoResource;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Resource getLogoResource() {
        return logoResource;
    }

    public void setLogoResource(Resource logoResource) {
        this.logoResource = logoResource;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return rating == team.rating
                && discipline == team.discipline
                && Objects.equals(name, team.name)
                && Objects.equals(logoResource, team.logoResource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discipline, name, logoResource, rating);
    }

    @Override
    public String toString() {
        return "Team{" +
                "discipline=" + discipline +
                ", name='" + name + '\'' +
                ", logoResource=" + logoResource +
                ", rating=" + rating +
                '}';
    }
}
