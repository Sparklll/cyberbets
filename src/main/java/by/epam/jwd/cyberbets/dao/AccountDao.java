package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.dto.CreateAccountDto;


import java.math.BigDecimal;
import java.util.Optional;

public interface AccountDao {
    Optional<Account> findAccountById(int id) throws DaoException;
    Optional<Account> findAccountByEmail(String email) throws DaoException;
    boolean createAccount(CreateAccountDto createAccountDto) throws DaoException;
    boolean updateAccount(Account account) throws DaoException;
    boolean updateAccountBalance(int id, BigDecimal balance) throws DaoException;
}
