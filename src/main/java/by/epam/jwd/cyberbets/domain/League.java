package by.epam.jwd.cyberbets.domain;

import java.util.Objects;

public class League extends Entity {
    private static final long serialVersionUID = 4942205941516690400L;

    private String name;
    private Discipline discipline;
    private Resource iconResource;

    public League(int id, String name, Discipline discipline, Resource iconResource) {
        super(id);
        this.name = name;
        this.discipline = discipline;
        this.iconResource = iconResource;
    }

    public League(String name, Discipline discipline, Resource iconResource) {
        this.name = name;
        this.discipline = discipline;
        this.iconResource = iconResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Resource getIconResource() {
        return iconResource;
    }

    public void setIconResource(Resource iconResource) {
        this.iconResource = iconResource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        League league = (League) o;
        return Objects.equals(name, league.name)
                && discipline == league.discipline
                && Objects.equals(iconResource, league.iconResource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, discipline, iconResource);
    }

    @Override
    public String toString() {
        return "League{" +
                "name='" + name + '\'' +
                ", discipline=" + discipline +
                ", iconResource=" + iconResource +
                '}';
    }
}
