package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.ResourceDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.epam.jwd.cyberbets.dao.impl.DatabaseMetadata.*;

public class ResourceDaoImpl implements ResourceDao {
    ResourceDaoImpl() {

    }

    private static final String FIND_RESOURCE_BY_ID = "select * from resource where id = ?";
    private static final String CREATE_RESOURCE = "insert into resource (path) values (?) returning id";
    private static final String UPDATE_RESOURCE = "update resource set path = ? where id = ?";
    private static final String DELETE_RESOURCE = "delete from resource where id = ?";

    @Override
    public Optional<Resource> findResourceById(int resourceId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_RESOURCE_BY_ID)) {
            ps.setInt(1, resourceId);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<Resource> resourceOptional = Optional.empty();
                while (rs.next()) {
                    Resource resource = new Resource(
                            rs.getInt(ID),
                            rs.getString(PATH));
                    resourceOptional = Optional.of(resource);
                }
                return resourceOptional;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int createResource(String resourcePath) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_RESOURCE)) {
            ps.setString(1, resourcePath);
            ps.execute();
            try(ResultSet rs = ps.getResultSet()) {
                rs.next();
                return rs.getInt(ID);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateResource(Resource resource) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_RESOURCE)) {
            ps.setString(1, resource.getPath());
            ps.setInt(2, resource.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteResource(int resourceId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_RESOURCE)) {
            ps.setInt(1, resourceId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
