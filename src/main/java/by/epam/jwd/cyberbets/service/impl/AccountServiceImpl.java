package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.dao.AccountDao;
import by.epam.jwd.cyberbets.dao.ResourceDao;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.dao.exception.DaoException;
import by.epam.jwd.cyberbets.dao.impl.transactional.AccountManager;
import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.dto.CreateAccountDto;
import by.epam.jwd.cyberbets.domain.dto.LoginDto;
import by.epam.jwd.cyberbets.domain.dto.RegisterDto;
import by.epam.jwd.cyberbets.domain.dto.UpdatePasswordDto;
import by.epam.jwd.cyberbets.service.AccountService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.util.ResourceManager;
import by.epam.jwd.cyberbets.util.ResourceManager.ResourceType;
import by.epam.jwd.cyberbets.util.exception.UtilException;
import com.password4j.Password;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.OptionalInt;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao = DaoProvider.INSTANCE.getAccountDao();
    private final ResourceDao resourceDao = DaoProvider.INSTANCE.getResourceDao();
    private final AccountManager accountManager = DaoProvider.INSTANCE.getAccountManager();

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
    public Optional<BigDecimal> getAccountBalance(int accountId) throws ServiceException {
        try {
            return accountDao.getAccountBalance(accountId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getTotalAccountsNumber() throws ServiceException {
        try {
            return accountDao.getTotalAccountsNumber();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getTotalAccountsNumberByPeriod(int daysNumber) throws ServiceException {
        try {
            return accountDao.getTotalAccountsNumberByPeriod(daysNumber);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void createAccount(RegisterDto registerDto) throws ServiceException {
        try {
            String email = registerDto.email();
            String password = registerDto.password();

            String passwordHash = Password.hash(password)
                                        .addRandomSalt()
                                        .withArgon2()
                                        .getResult();
            CreateAccountDto createAccountDto = new CreateAccountDto(email, passwordHash);
            accountDao.createAccount(createAccountDto);
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

    @Override
    public void updateAccount(Account account) throws ServiceException {
        try {
            accountDao.updateAccount(account);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateAccountPassword(int accountId, UpdatePasswordDto updatePasswordDto) throws ServiceException {
        try {
            Optional<Account> foundAccountOptional = findAccountById(accountId);
            if (foundAccountOptional.isPresent()) {
                Account foundAccount = foundAccountOptional.get();
                String foundAccountPasswordHash = foundAccount.getPasswordHash();

                String currentPassword = updatePasswordDto.currentPassword();
                if (Password.check(currentPassword, foundAccountPasswordHash).withArgon2()) {
                    String newPassword = updatePasswordDto.newPassword();
                    String newPasswordHash = Password.hash(newPassword)
                            .addRandomSalt()
                            .withArgon2()
                            .getResult();
                    foundAccount.setPasswordHash(newPasswordHash);
                    accountDao.updateAccount(foundAccount);
                    return true;
                }
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateAccountAvatar(int accountId, String avatarBase64) throws ServiceException {
        try {
            Optional<Account> foundAccountOptional = findAccountById(accountId);
            if (foundAccountOptional.isPresent()) {
                Account foundAccount = foundAccountOptional.get();
                Resource avatarResource = foundAccount.getAvatarResource();

                //check for default avatarId (set by default after account creation in db)
                if(avatarResource.getId() != 1) {
                    ResourceManager.updateImage(ResourceType.ACCOUNT_AVATAR, avatarResource, avatarBase64);
                    resourceDao.updateResource(avatarResource);
                } else {
                    String newAvatarResourcePath = ResourceManager.uploadImage(ResourceType.ACCOUNT_AVATAR, avatarBase64);
                    int resourceId = resourceDao.createResource(newAvatarResourcePath);
                    Resource newAvatarResource = new Resource(resourceId, newAvatarResourcePath);
                    foundAccount.setAvatarResource(newAvatarResource);
                }
                accountDao.updateAccount(foundAccount);
                return true;
            }
            return false;
        } catch (DaoException | UtilException e) {
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
    public void addToAccountBalance(int accountId, BigDecimal amount) throws ServiceException {
        try {
            accountDao.addToAccountBalance(accountId, amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void subtractFromAccountBalance(int accountId, BigDecimal amount) throws ServiceException {
        try {
            accountDao.subtractFromAccountBalance(accountId, amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void performDeposit(int accountId, BigDecimal amount) throws ServiceException {
        try {
            accountManager.performDeposit(accountId, amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void performWithdraw(int accountId, BigDecimal amount) throws ServiceException {
        try {
            accountManager.performWithdraw(accountId, amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
