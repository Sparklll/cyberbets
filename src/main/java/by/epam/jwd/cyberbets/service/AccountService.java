package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.dto.LoginDto;
import by.epam.jwd.cyberbets.domain.dto.RegisterDto;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.OptionalInt;

public interface AccountService {
    Optional<Account> findAccountById(int accountId) throws ServiceException;
    Optional<Account> findAccountByEmail(String email) throws ServiceException;
    OptionalInt findIdByEmail(String email) throws ServiceException;
    public Optional<BigDecimal> getAccountBalance(int accountId) throws ServiceException;
    void createAccount(RegisterDto registerDto) throws ServiceException;
    void updateAccount(Account account) throws ServiceException;
    void updateAccountBalance(int accountId, BigDecimal balance) throws ServiceException;
    void addToAccountBalance(int accountId, BigDecimal amount) throws ServiceException;
    void subtractFromAccountBalance(int accountId, BigDecimal amount) throws ServiceException;
    boolean isAuthorizationValid(LoginDto loginDto) throws ServiceException;
}
