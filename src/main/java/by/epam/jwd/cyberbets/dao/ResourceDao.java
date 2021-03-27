package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.domain.Resource;

import java.util.Optional;

public interface ResourceDao {
    Optional<Resource> findResourceById(int resourceId);
    void createResource(String resourcePath);
    void updateResource(Resource resource);
    void deleteResource(int resourceId);
}
