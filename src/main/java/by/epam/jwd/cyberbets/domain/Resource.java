package by.epam.jwd.cyberbets.domain;

import java.util.Objects;

public class Resource extends Entity {
    private static final long serialVersionUID = -2187305935066935212L;

    private String path;

    public Resource(int id, String path) {
        super(id);
        this.path = path;
    }

    public Resource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(path, resource.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "path='" + path + '\'' +
                '}';
    }
}
