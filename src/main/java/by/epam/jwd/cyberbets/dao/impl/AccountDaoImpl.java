package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.AccountDao;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.dto.CreateAccountDto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {
    private static final String FIND_ACCOUNT_BY_ID = "select * from account where id = ?";
    private static final String FIND_ACCOUNT_BY_EMAIL = "select * from account where email = ?";
    private static final String CREATE_NEW_ACCOUNT = "insert into account (email, password_hash, salt) VALUES (?, ?, ?)";
    private static final String UPDATE_ACCOUNT = "update account set email = ?, password_hash = ?, salt = ?, balance = ?, role_id = ?, avatar_resource_id = ? where id = ?";
    private static final String UPDATE_ACCOUNT_BALANCE = "update account set balance = ? where id = ?";

    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String PASSWORD_HASH = "password_hash";
    private static final String PASSWORD_SALT = "salt";
    private static final String BALANCE = "balance";
    private static final String ROLE_ID = "role_id";
    private static final String AVATAR_RESOURCE_ID = "avatar_resource_id";

    @Override
    public Optional<Account> findAccountById(int id) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ACCOUNT_BY_ID)) {
            ps.setInt(1, id);
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
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ACCOUNT_BY_EMAIL)) {
            ps.setString(1, email);
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
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void createAccount(CreateAccountDto createAccountDto) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_NEW_ACCOUNT)) {
            ps.setString(1, createAccountDto.email());
            ps.setString(2, createAccountDto.password());
            ps.setString(3, createAccountDto.salt());
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
            ps.setString(3, account.getSalt());
            ps.setBigDecimal(4, account.getBalance());
            ps.setInt(5, account.getRoleId());
            ps.setInt(6, account.getAvatarResourceId());
            ps.setInt(7, account.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void updateAccountBalance(int id, BigDecimal balance) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE)) {
            ps.setBigDecimal(1, balance);
            ps.setInt(2, id);
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
                rs.getString(PASSWORD_SALT),
                rs.getBigDecimal(BALANCE),
                rs.getInt(ROLE_ID),
                rs.getInt(AVATAR_RESOURCE_ID)
        );
    }
}
