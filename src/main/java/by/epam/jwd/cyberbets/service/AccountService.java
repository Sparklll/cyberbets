package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.dto.LoginDto;
import by.epam.jwd.cyberbets.domain.dto.RegisterDto;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountService {
    Optional<Account> findAccountById(int id) throws ServiceException;
    Optional<Account> findAccountByEmail(String email) throws ServiceException;
    void createAccount(RegisterDto registerDto) throws ServiceException;
    void updateAccount(Account account) throws ServiceException;
    void updateAccountBalance(int id, BigDecimal balance) throws ServiceException;
    boolean isAuthorized(LoginDto loginDto) throws ServiceException;
}
