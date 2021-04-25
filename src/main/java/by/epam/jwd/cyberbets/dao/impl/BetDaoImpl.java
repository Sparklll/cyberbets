package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.BetDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.Bet.Upshot;
import by.epam.jwd.cyberbets.domain.dto.BetDto;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.jwd.cyberbets.dao.impl.DatabaseMetadata.*;

public class BetDaoImpl implements BetDao {
    private final Connection transactionConnection;
    private final boolean isTransactional;

    private static final String FIND_BET_BY_ID = "select * from bet where id = ?";
    private static final String FIND_BET_BY_ACCOUNT_ID_AND_EVENT_RESULT_ID = "select * from bet where account_id = ? and event_result_id = ?";
    private static final String FIND_ALL_BETS_BY_ACCOUNT_ID = "select * from bet where account_id = ?";
    private static final String FIND_ALL_BETS_BY_EVENT_ID = """
            select b.id, b.account_id, b.event_result_id, b.amount, b.date, b.upshot_type_id
            from bet b
            inner join event_result er on er.id = b.event_result_id
            where er.event_id = ?
            """;
    private static final String FIND_ALL_BETS_BY_ACCOUNT_ID_AND_EVENT_ID = """
            select b.id, b.account_id, b.event_result_id, b.amount, b.date, b.upshot_type_id
            from bet b
            inner join event_result er on er.id = b.event_result_id
            where b.account_id = ? and er.event_id = ?
            """;
    private static final String FIND_ALL_BETS_BY_EVENT_RESULT_ID = "select * from bet where event_result_id = ?";
    private static final String GET_TOTAL_AMOUNT_OF_BETS = """
            select coalesce(sum(amount), 0) as total_amount
            from bet
            where event_result_id = ?
            """;
    private static final String GET_TOTAL_AMOUNT_OF_BETS_FOR_UPSHOT = """
            select coalesce(sum(amount), 0) as total_amount
            from bet
            where event_result_id = ? and upshot_type_id = ?
            """;
    private static final String CREATE_BET = "insert into bet (account_id, event_result_id, amount, date, upshot_type_id) values (?, ?, ?, ?, ?)";
    private static final String UPDATE_BET = "update bet set account_id = ?, event_result_id = ?, amount = ?, date = ?, upshot_type_id = ? where id = ?";
    private static final String DELETE_BET = "delete from bet where id = ?";

    BetDaoImpl() {
        transactionConnection = null;
        isTransactional = false;
    }

    public BetDaoImpl(Connection transactionConnection) {
        this.transactionConnection = transactionConnection;
        isTransactional = true;
    }

    private Connection getConnection() {
        return isTransactional
                ? transactionConnection
                : ConnectionPool.INSTANCE.getConnection();
    }

    @Override
    public List<Bet> findAllBetsByAccountId(int accountId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_BETS_BY_ACCOUNT_ID)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Bet> bets = new ArrayList<>();
                while (rs.next()) {
                    Bet bet = mapRow(rs);
                    bets.add(bet);
                }
                return bets;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Bet> findAllBetsByEventId(int eventId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_BETS_BY_EVENT_ID)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Bet> bets = new ArrayList<>();
                while (rs.next()) {
                    Bet bet = mapRow(rs);
                    bets.add(bet);
                }
                return bets;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Bet> findAllBetsByAccountIdAndEventId(int accountId, int eventId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_BETS_BY_ACCOUNT_ID_AND_EVENT_ID)) {
            ps.setInt(1, accountId);
            ps.setInt(2, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Bet> bets = new ArrayList<>();
                while (rs.next()) {
                    Bet bet = mapRow(rs);
                    bets.add(bet);
                }
                return bets;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Bet> findAllBetsByEventResultId(int eventResultId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_BETS_BY_EVENT_RESULT_ID)) {
            ps.setInt(1, eventResultId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Bet> bets = new ArrayList<>();
                while (rs.next()) {
                    Bet bet = mapRow(rs);
                    bets.add(bet);
                }
                return bets;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Bet> findBetByAccountIdAndEventResultId(int accountId, int eventResultId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_BET_BY_ACCOUNT_ID_AND_EVENT_RESULT_ID)) {
            ps.setInt(1, accountId);
            ps.setInt(2, eventResultId);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<Bet> betOptional = Optional.empty();
                if (rs.next()) {
                    Bet bet = mapRow(rs);
                    betOptional = Optional.of(bet);
                }
                return betOptional;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Bet> findBetById(int betId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_BET_BY_ID)) {
            ps.setInt(1, betId);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<Bet> betOptional = Optional.empty();
                if (rs.next()) {
                    Bet bet = mapRow(rs);
                    betOptional = Optional.of(bet);
                }
                return betOptional;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public BigDecimal getTotalAmountOfBets(int eventResultId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(GET_TOTAL_AMOUNT_OF_BETS)) {
            ps.setInt(1, eventResultId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getBigDecimal(TOTAL_AMOUNT);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public BigDecimal getTotalAmountOfBetsForUpshot(int eventResultId, Upshot upshot) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(GET_TOTAL_AMOUNT_OF_BETS_FOR_UPSHOT)) {
            ps.setInt(1, eventResultId);
            ps.setInt(2, upshot.getId());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getBigDecimal(TOTAL_AMOUNT);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void createBet(BetDto betDto) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(CREATE_BET)) {
            ps.setInt(1, betDto.accountId());
            ps.setInt(2, betDto.eventResultId());
            ps.setBigDecimal(3, betDto.amount());
            ps.setTimestamp(4, Timestamp.from(Instant.now()));
            ps.setInt(5, betDto.upshotId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateBet(Bet bet) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(UPDATE_BET)) {
            ps.setInt(1, bet.getAccountId());
            ps.setInt(2, bet.getEventResultId());
            ps.setBigDecimal(3, bet.getAmount());
            ps.setTimestamp(4, Timestamp.from(Instant.now()));
            ps.setInt(5, bet.getUpshot().getId());
            ps.setInt(6, bet.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteBet(int betId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(DELETE_BET)) {
            ps.setInt(1, betId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Bet mapRow(ResultSet rs) throws SQLException {
        return new Bet(rs.getInt(ID),
                rs.getInt(ACCOUNT_ID),
                rs.getInt(EVENT_RESULT_ID),
                Upshot.getUpshotById(rs.getInt(UPSHOT_TYPE_ID)).get(),
                rs.getBigDecimal(AMOUNT),
                rs.getTimestamp(DATE).toInstant());
    }
}
