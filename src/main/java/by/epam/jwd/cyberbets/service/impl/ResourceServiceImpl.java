package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.DaoProvider;
import by.epam.jwd.cyberbets.dao.ResourceDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.service.ResourceService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.util.Optional;

public class ResourceServiceImpl implements ResourceService {
    private final ResourceDao resourceDao = DaoProvider.INSTANCE.getResourceDao();

    @Override
    public Optional<Resource> findResourceById(int resourceId) throws ServiceException {
        try {
            return resourceDao.findResourceById(resourceId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int createResource(String resourcePath) throws ServiceException {
        try {
            return resourceDao.createResource(resourcePath);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateResource(Resource resource) throws ServiceException {
        try {
            resourceDao.updateResource(resource);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteResource(int resourceId) throws ServiceException {
        try {
            resourceDao.deleteResource(resourceId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
