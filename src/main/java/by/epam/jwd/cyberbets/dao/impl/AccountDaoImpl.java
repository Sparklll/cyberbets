package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.AccountDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.dto.CreateAccountDto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static by.epam.jwd.cyberbets.dao.impl.DatabaseMetadata.*;

public class AccountDaoImpl implements AccountDao {
    private final Connection transactionConnection;
    private final boolean isTransactional;

    private static final String FIND_ALL_ACCOUNTS_BY_REGISTRATION_PERIOD = """
            select a.id, a.email, a.password_hash, a.role_id, a.balance, a.avatar_resource_id, r.path, a.registration_date
            from account a
            inner join resource r on r.id = a.avatar_resource_id
            where registration_date >= current_timestamp - ? * interval '1' day
            """;
    private static final String FIND_ACCOUNT_BY_ID = """
            select a.id, a.email, a.password_hash, a.role_id, a.balance, a.avatar_resource_id, r.path, a.registration_date
            from account a
            inner join resource r on r.id = a.avatar_resource_id
            where a.id = ?
            """;
    private static final String FIND_ACCOUNT_BY_EMAIL = """
            select a.id, a.email, a.password_hash, a.role_id, a.balance, a.avatar_resource_id, r.path, a.registration_date
            from account a
            inner join resource r on r.id = a.avatar_resource_id
            where a.email = ?
            """;
    private static final String FIND_ID_BY_EMAIL = "select id from account where email = ?";
    private static final String GET_TOTAL_ACCOUNTS_NUMBER = "select count(*) from account";
    private static final String GET_TOTAL_ACCOUNTS_NUMBER_BY_PERIOD = """
            select count(*)
            from account
            where registration_date >= current_timestamp - ? * interval '1' day
            """;
    private static final String GET_ACCOUNT_BALANCE = "select balance from account where id = ?";
    private static final String CREATE_ACCOUNT = "insert into account (email, password_hash) VALUES (?, ?)";
    private static final String UPDATE_ACCOUNT = "update account set email = ?, password_hash = ?, balance = ?, role_id = ?, avatar_resource_id = ? where id = ?";
    private static final String UPDATE_ACCOUNT_BALANCE = "update account set balance = ? where id = ?";
    private static final String ADD_TO_ACCOUNT_BALANCE = "update account set balance = balance + ? where id = ?";
    private static final String SUBTRACT_FROM_ACCOUNT_BALANCE = "update account set balance = balance - ? where id = ?";

    AccountDaoImpl() {
        transactionConnection = null;
        isTransactional = false;
    }

    public AccountDaoImpl(Connection transactionConnection) {
        this.transactionConnection = transactionConnection;
        isTransactional = true;
    }

    private Connection getConnection() {
        return isTransactional
                ? transactionConnection
                : ConnectionPool.INSTANCE.getConnection();
    }


    @Override
    public List<Account> findAllAccountsByRegistrationPeriod(int daysNumber) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_ACCOUNTS_BY_REGISTRATION_PERIOD)) {
            ps.setInt(1, daysNumber);
            try (ResultSet rs = ps.executeQuery()) {
                List<Account> accounts = new ArrayList<>();
                while (rs.next()) {
                    Account account = mapRow(rs);
                    accounts.add(account);
                }
                return accounts;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Account> findAccountById(int accountId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ACCOUNT_BY_ID)) {
            ps.setInt(1, accountId);
            return getAccount(ps);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ACCOUNT_BY_EMAIL)) {
            ps.setString(1, email);
            return getAccount(ps);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Optional<Account> getAccount(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            Optional<Account> accountOptional = Optional.empty();
            if (rs.next()) {
                Account account = mapRow(rs);
                accountOptional = Optional.of(account);
            }
            return accountOptional;
        }
    }

    @Override
    public OptionalInt findIdByEmail(String email) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ID_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                OptionalInt idOptional = OptionalInt.empty();
                if (rs.next()) {
                    int id = rs.getInt(ID);
                    idOptional = OptionalInt.of(id);
                }
                return idOptional;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<BigDecimal> getAccountBalance(int accountId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(GET_ACCOUNT_BALANCE)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<BigDecimal> balanceOptional = Optional.empty();
                if (rs.next()) {
                    BigDecimal balance = rs.getBigDecimal(BALANCE);
                    balanceOptional = Optional.of(balance);
                }
                return balanceOptional;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int getTotalAccountsNumber() throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(GET_TOTAL_ACCOUNTS_NUMBER)) {
            ps.execute();
            try (ResultSet rs = ps.getResultSet()) {
                rs.next();
                return rs.getInt(COUNT);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int getTotalAccountsNumberByPeriod(int daysNumber) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(GET_TOTAL_ACCOUNTS_NUMBER_BY_PERIOD)) {
            ps.setInt(1, daysNumber);
            ps.execute();
            try (ResultSet rs = ps.getResultSet()) {
                rs.next();
                return rs.getInt(COUNT);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void createAccount(CreateAccountDto createAccountDto) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(CREATE_ACCOUNT)) {
            ps.setString(1, createAccountDto.email());
            ps.setString(2, createAccountDto.passwordHash());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateAccount(Account account) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT)) {
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getPasswordHash());
            ps.setBigDecimal(3, account.getBalance());
            ps.setInt(4, account.getRole().getId());
            ps.setInt(5, account.getAvatarResource().getId());
            ps.setInt(6, account.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateAccountBalance(int accountId, BigDecimal balance) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE)) {
            ps.setBigDecimal(1, balance);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void addToAccountBalance(int accountId, BigDecimal amount) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(ADD_TO_ACCOUNT_BALANCE)) {
            ps.setBigDecimal(1, amount);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void subtractFromAccountBalance(int accountId, BigDecimal amount) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(SUBTRACT_FROM_ACCOUNT_BALANCE)) {
            ps.setBigDecimal(1, amount);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt(ID),
                rs.getString(EMAIL),
                rs.getString(PASSWORD_HASH),
                rs.getBigDecimal(BALANCE),
                Role.getRoleById(rs.getInt(ROLE_ID)),
                new Resource(rs.getInt(AVATAR_RESOURCE_ID), rs.getString(PATH)),
                rs.getTimestamp(REGISTRATION_DATE).toInstant());
    }
}
