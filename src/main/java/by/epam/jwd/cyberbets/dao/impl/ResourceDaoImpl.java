package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.ResourceDao;
import by.epam.jwd.cyberbets.domain.Resource;

import java.util.Optional;

public class ResourceDaoImpl implements ResourceDao {
    @Override
    public Optional<Resource> findResourceById(int resourceId) {
        return Optional.empty();
    }

    @Override
    public void createResource(String resourcePath) {

    }

    @Override
    public void updateResource(Resource resource) {

    }

    @Override
    public void deleteResource(int resourceId) {

    }
}
