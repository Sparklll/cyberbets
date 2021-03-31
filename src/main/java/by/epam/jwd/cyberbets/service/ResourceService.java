package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.util.Optional;

public interface ResourceService {
    Optional<Resource> findResourceById(int resourceId) throws ServiceException;
    int createResource(String resourcePath) throws ServiceException;
    void updateResource(Resource resource) throws ServiceException;
    void deleteResource(int resourceId) throws ServiceException;
}
