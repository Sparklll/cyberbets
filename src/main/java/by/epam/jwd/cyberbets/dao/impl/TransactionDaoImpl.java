package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.TransactionDao;
import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Transaction;
import by.epam.jwd.cyberbets.domain.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.jwd.cyberbets.dao.impl.DatabaseMetadata.*;

public class TransactionDaoImpl implements TransactionDao {
    private final Connection transactionConnection;
    private final boolean isTransactional;

    private static final String FIND_ALL_TRANSACTIONS_BY_ACCOUNT_ID = "select * from transaction where account_id = ?";
    private static final String FIND_ALL_TRANSACTIONS_BY_PERIOD = "select * from transaction where date >= current_timestamp - ? * interval '1' day";
    private static final String FIND_TRANSACTION_BY_ID = "select * from transaction where id = ?";
    private static final String CREATE_TRANSACTION = "insert into transaction (type_id, account_id, amount) values (?, ?, ?) returning id";
    private static final String UPDATE_TRANSACTION = "update transaction set type_id = ?, account_id = ?, amount = ?, date = ? where id = ?";
    private static final String DELETE_TRANSACTION = "delete from transaction where id = ?";

    TransactionDaoImpl() {
        transactionConnection = null;
        isTransactional = false;
    }

    public TransactionDaoImpl(Connection transactionConnection) {
        this.transactionConnection = transactionConnection;
        this.isTransactional = true;
    }

    private Connection getConnection() {
        return isTransactional
                ? transactionConnection
                : ConnectionPool.INSTANCE.getConnection();
    }

    @Override
    public List<Transaction> findAllTransactionsByAccountId(int accountId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_TRANSACTIONS_BY_ACCOUNT_ID)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Transaction> transactions = new ArrayList<>();
                while (rs.next()) {
                    Transaction transaction = mapRow(rs);
                    transactions.add(transaction);
                }
                return transactions;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Transaction> findAllTransactionsByPeriod(int daysNumber) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_TRANSACTIONS_BY_PERIOD)) {
            ps.setInt(1, daysNumber);
            try (ResultSet rs = ps.executeQuery()) {
                List<Transaction> transactions = new ArrayList<>();
                while (rs.next()) {
                    Transaction transaction = mapRow(rs);
                    transactions.add(transaction);
                }
                return transactions;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Transaction> findTransactionById(int transactionId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(FIND_TRANSACTION_BY_ID)) {
            ps.setInt(1, transactionId);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<Transaction> transactionOptional = Optional.empty();
                if (rs.next()) {
                    Transaction transaction = mapRow(rs);
                    transactionOptional = Optional.of(transaction);
                }
                return transactionOptional;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int createTransaction(Transaction transaction) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(CREATE_TRANSACTION)) {
            ps.setInt(1, transaction.getTransactionType().getId());
            ps.setInt(2, transaction.getAccountId());
            ps.setBigDecimal(3, transaction.getAmount());
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
    public void updateTransaction(Transaction transaction) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(UPDATE_TRANSACTION)) {
            ps.setInt(1, transaction.getTransactionType().getId());
            ps.setInt(2, transaction.getAccountId());
            ps.setBigDecimal(3, transaction.getAmount());
            ps.setTimestamp(4, Timestamp.from(transaction.getDate()));
            ps.setInt(5, transaction.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteTransaction(int transactionId) throws DaoException {
        Connection connection = getConnection();

        try (Connection connectionResource = isTransactional ? null : connection;
             PreparedStatement ps = connection.prepareStatement(DELETE_TRANSACTION)) {
            ps.setInt(1, transactionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Transaction mapRow(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getInt(ID),
                rs.getInt(ACCOUNT_ID),
                TransactionType.getTransactionTypeById(rs.getInt(TYPE_ID)).get(),
                rs.getBigDecimal(AMOUNT),
                rs.getTimestamp(DATE).toInstant()
        );
    }
}
