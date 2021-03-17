package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.impl.AccountDaoImpl;

public enum DaoProvider {
    INSTANCE;

    private final AccountDao accountDao = new AccountDaoImpl();

    public AccountDao getAccountDao() {
        return accountDao;
    }
}
