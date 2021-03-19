package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.AccountDao;
import by.epam.jwd.cyberbets.dao.DaoProvider;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.dto.CreateAccountDto;
import by.epam.jwd.cyberbets.domain.dto.RegisterDto;
import by.epam.jwd.cyberbets.service.AccountService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao = DaoProvider.INSTANCE.getAccountDao();

    @Override
    public Optional<Account> findAccountById(int id) throws ServiceException {
        try {
            return accountDao.findAccountById(id);
        } catch (DaoException throwables) {
            throw new ServiceException(throwables);
        }
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) throws ServiceException {
        try {
            return accountDao.findAccountByEmail(email);
        } catch (DaoException throwables) {
            throw new ServiceException(throwables);
        }
    }

    @Override
    public void createAccount(RegisterDto registerDto) throws ServiceException {
        try {
            String email = registerDto.email();
            String password = registerDto.password();
            // make salt with crypto
            CreateAccountDto createAccountDto = new CreateAccountDto(email, password, password);
           accountDao.createAccount(createAccountDto);
        } catch (DaoException throwables) {
            throw new ServiceException(throwables);
        }
    }

    @Override
    public void updateAccount(Account account) throws ServiceException {

    }

    @Override
    public void updateAccountBalance(int id, BigDecimal balance) throws ServiceException {

    }
}
