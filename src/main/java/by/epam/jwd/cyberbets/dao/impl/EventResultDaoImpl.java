package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.EventResultDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.EventOutcomeType;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.ResultStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.jwd.cyberbets.dao.impl.DatabaseMetadata.*;

public class EventResultDaoImpl implements EventResultDao {
    private final Connection transactionConnection;
    private final boolean isTransactional;

    private static final String FIND_ALL_EVENT_RESULTS = "select * from event_result";
    private static final String FIND_ALL_EVENT_RESULTS_BY_EVENT_ID = "select * from event_result where event_id = ?";
    private static final String FIND_EVENT_RESULTS_BY_ID = "select * from event_result where id = ?";
    private static final String CREATE_EVENT_RESULT = "insert into event_result (event_id, outcome_type_id, result_status_id) values (?, ?, ?) returning id";
    private static final String UPDATE_EVENT_RESULT = "update event_result set event_id = ?, outcome_type_id = ?, result_status_id = ? where id = ?";
    private static final String DELETE_EVENT_RESULT = "delete from event_result where id = ?";

    EventResultDaoImpl() {
        transactionConnection = null;
        isTransactional = false;
    }

    EventResultDaoImpl(Connection transactionConnection) {
        this.transactionConnection = transactionConnection;
        isTransactional = true;
    }

    private Connection getConnection() {
        return isTransactional
                ? transactionConnection
                : ConnectionPool.INSTANCE.getConnection();
    }

    @Override
    public List<EventResult> findAll() throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_EVENT_RESULTS)) {
            return getEvents(ps);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<EventResult> findAllByEventId(int eventId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_EVENT_RESULTS_BY_EVENT_ID)) {
            ps.setInt(1, eventId);
            return getEvents(ps);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private List<EventResult> getEvents(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            List<EventResult> eventResults = new ArrayList<>();
            while (rs.next()) {
                EventResult eventResult = mapRow(rs);
                eventResults.add(eventResult);
            }
            return eventResults;
        }
    }

    @Override
    public Optional<EventResult> findEventResultById(int eventResultId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_EVENT_RESULTS_BY_ID)) {
            ps.setInt(1, eventResultId);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<EventResult> eventResultOptional = Optional.empty();
                while (rs.next()) {
                    EventResult eventResult = mapRow(rs);
                    eventResultOptional = Optional.of(eventResult);
                }
                return eventResultOptional;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int createEventResult(EventResult eventResult) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(CREATE_EVENT_RESULT)) {
            ps.setInt(1, eventResult.getEventId());
            ps.setInt(2, eventResult.getEventOutcomeType().getId());
            ps.setInt(3, eventResult.getResultStatus().getId());
            ps.execute();
            try (ResultSet rs = ps.getResultSet()) {
                rs.next();
                return rs.getInt(ID);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateEventResult(EventResult eventResult) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(UPDATE_EVENT_RESULT)) {
            ps.setInt(1, eventResult.getEventId());
            ps.setInt(2, eventResult.getEventOutcomeType().getId());
            ps.setInt(3, eventResult.getResultStatus().getId());
            ps.setInt(4, eventResult.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteEventResult(int eventResultId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(DELETE_EVENT_RESULT)) {
            ps.setInt(1, eventResultId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private EventResult mapRow(ResultSet rs) throws SQLException{
        int id = rs.getInt(ID);
        int eventId = rs.getInt(EVENT_ID);
        EventOutcomeType eventOutcomeType = EventOutcomeType.getEventOutcomeTypeById(rs.getInt(OUTCOME_TYPE_ID)).get();
        ResultStatus resultStatus = ResultStatus.getResultStatusById(rs.getInt(RESULT_STATUS_ID)).get();

        return new EventResult(id, eventId, eventOutcomeType, resultStatus);
    }
}
