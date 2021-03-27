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
import java.util.Optional;
import java.util.OptionalInt;

import static by.epam.jwd.cyberbets.dao.impl.DatabaseMetadata.*;

public class AccountDaoImpl implements AccountDao {
    private static final String FIND_ACCOUNT_BY_ID = """
            select a.id, a.email, a.password_hash, a.role_id, a.balance, a.avatar_resource_id, rs.path
            from account a
            inner join resource rs on rs.id = a.avatar_resource_id
            where id = ?
            """;
    private static final String FIND_ACCOUNT_BY_EMAIL = """
            select a.id, a.email, a.password_hash, a.role_id, a.balance, a.avatar_resource_id, rs.path
            from account a
            inner join resource rs on rs.id = a.avatar_resource_id
            where email = ?
            """;
    private static final String FIND_ID_BY_EMAIL = "select id from account where email = ?";
    private static final String CREATE_NEW_ACCOUNT = "insert into account (email, password_hash) VALUES (?, ?)";
    private static final String UPDATE_ACCOUNT = "update account set email = ?, password_hash = ?, balance = ?, role_id = ?, avatar_resource_id = ? where id = ?";
    private static final String UPDATE_ACCOUNT_BALANCE = "update account set balance = ? where id = ?";

    @Override
    public Optional<Account> findAccountById(int accountId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ACCOUNT_BY_ID)) {
            ps.setInt(1, accountId);
            return getAccount(ps);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ACCOUNT_BY_EMAIL)) {
            ps.setString(1, email);
            return getAccount(ps);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private Optional<Account> getAccount(PreparedStatement ps) throws DaoException {
        try (ResultSet rs = ps.executeQuery()) {
            Optional<Account> accountOptional = Optional.empty();
            if (rs.next()) {
                Account account = mapRow(rs);
                accountOptional = Optional.of(account);
            }
            return accountOptional;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public OptionalInt findIdByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ID_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                OptionalInt idOptional = OptionalInt.empty();
                if (rs.next()) {
                    int id = rs.getInt(ID);
                    idOptional = OptionalInt.of(id);
                }
                return idOptional;
            } catch (SQLException throwables) {
                throw new DaoException(throwables);
            }
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void createAccount(CreateAccountDto createAccountDto) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_NEW_ACCOUNT)) {
            ps.setString(1, createAccountDto.email());
            ps.setString(2, createAccountDto.passwordHash());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void updateAccount(Account account) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT)) {
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getPasswordHash());
            ps.setBigDecimal(3, account.getBalance());
            ps.setInt(4, account.getRole().getId());
            ps.setInt(5, account.getAvatarResource().getId());
            ps.setInt(6, account.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void updateAccountBalance(int accountId, BigDecimal balance) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE)) {
            ps.setBigDecimal(1, balance);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt(ID),
                rs.getString(EMAIL),
                rs.getString(PASSWORD_HASH),
                rs.getBigDecimal(BALANCE),
                Role.getRoleById(rs.getInt(ROLE_ID)),
                new Resource(rs.getInt(AVATAR_RESOURCE_ID), rs.getString(PATH))
        );
    }
}
