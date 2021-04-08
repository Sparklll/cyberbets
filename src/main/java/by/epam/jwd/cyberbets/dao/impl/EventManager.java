package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.AccountDao;
import by.epam.jwd.cyberbets.dao.BetDao;
import by.epam.jwd.cyberbets.dao.EventDao;
import by.epam.jwd.cyberbets.dao.EventResultDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public class EventManager {
    EventManager() {

    }

    public void createEvent() throws DaoException {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try(connection) {
            connection.setAutoCommit(false);

            final EventDao eventDao = new EventDaoImpl(connection);
            final EventResultDao eventResultDao = new EventResultDaoImpl(connection);
            final BetDao betDao = new BetDaoImpl(connection);
            final AccountDao accountDao = new AccountDaoImpl(connection);



            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                throw new DaoException(sqlException);
            }
        }
    }

    public void updateEvent() throws DaoException {

    }

    public void deleteEvent() throws DaoException {

    }
}
