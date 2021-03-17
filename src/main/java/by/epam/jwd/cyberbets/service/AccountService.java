package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.dto.CreateAccountDto;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountService {
    Optional<Account> findAccountById(int id) throws ServiceException;
    Optional<Account> findAccountByEmail(String email) throws ServiceException;
    boolean createAccount(CreateAccountDto createAccountDto) throws ServiceException;
    boolean updateAccount(Account account) throws ServiceException;
    boolean updateAccountBalance(int id, BigDecimal balance) throws ServiceException;
}
