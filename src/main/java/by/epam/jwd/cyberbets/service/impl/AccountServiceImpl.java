package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.AccountDao;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.dto.CreateAccountDto;
import by.epam.jwd.cyberbets.domain.dto.LoginDto;
import by.epam.jwd.cyberbets.domain.dto.RegisterDto;
import by.epam.jwd.cyberbets.service.AccountService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import com.password4j.Hash;
import com.password4j.Password;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.OptionalInt;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao = DaoProvider.INSTANCE.getAccountDao();

    AccountServiceImpl() {

    }

    @Override
    public Optional<Account> findAccountById(int accountId) throws ServiceException {
        try {
            return accountDao.findAccountById(accountId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) throws ServiceException {
        try {
            return accountDao.findAccountByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public OptionalInt findIdByEmail(String email) throws ServiceException {
        try {
            return accountDao.findIdByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void createAccount(RegisterDto registerDto) throws ServiceException {
        try {
            String email = registerDto.email();
            String password = registerDto.password();

            Hash passwordHash = Password.hash(password)
                                        .addRandomSalt()
                                        .withArgon2();
            CreateAccountDto createAccountDto = new CreateAccountDto(email, passwordHash.getResult());
            accountDao.createAccount(createAccountDto);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateAccount(Account account) throws ServiceException {
        try {
            accountDao.updateAccount(account);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateAccountBalance(int accountId, BigDecimal balance) throws ServiceException {
        try {
            accountDao.updateAccountBalance(accountId, balance);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isAuthorizationValid(LoginDto loginDto) throws ServiceException {
        String loginDtoEmail = loginDto.email();
        String loginDtoPassword = loginDto.password();

        Optional<Account> foundAccountOptional = findAccountByEmail(loginDtoEmail);
        if (foundAccountOptional.isPresent()) {
            Account foundAccount = foundAccountOptional.get();
            String foundAccountPasswordHash = foundAccount.getPasswordHash();
            return Password.check(loginDtoPassword, foundAccountPasswordHash).withArgon2();
        }
        return false;
    }
}
