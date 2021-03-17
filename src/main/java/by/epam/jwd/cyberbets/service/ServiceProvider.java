package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.service.impl.AccountServiceImpl;

public enum ServiceProvider {
    INSTANCE;

    private final AccountService accountService = new AccountServiceImpl();

    public AccountService getAccountService() {
        return accountService;
    }
}
