package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Resource;

import java.util.Optional;

public interface ResourceDao {
    Optional<Resource> findResourceById(int resourceId) throws DaoException;
    int createResource(String resourcePath) throws DaoException;
    void updateResource(Resource resource) throws DaoException;
    void deleteResource(int resourceId) throws DaoException;
}
