package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.dto.CreateAccountDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface AccountDao {
    List<Account> findAllAccountsByRegistrationPeriod(int daysNumber) throws DaoException;
    Optional<Account> findAccountById(int accountId) throws DaoException;
    Optional<Account> findAccountByEmail(String email) throws DaoException;
    OptionalInt findIdByEmail(String email) throws DaoException;
    Optional<BigDecimal> getAccountBalance(int accountId) throws DaoException;
    int getTotalAccountsNumber() throws DaoException;
    int getTotalAccountsNumberByPeriod(int daysNumber) throws DaoException;
    void createAccount(CreateAccountDto createAccountDto) throws DaoException;
    void updateAccount(Account account) throws DaoException;
    void updateAccountBalance(int accountId, BigDecimal balance) throws DaoException;
    void addToAccountBalance(int accountId, BigDecimal amount) throws DaoException;
    void subtractFromAccountBalance(int accountId, BigDecimal amount) throws DaoException;
}
