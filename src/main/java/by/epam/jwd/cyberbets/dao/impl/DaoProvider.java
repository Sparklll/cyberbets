package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.*;
import by.epam.jwd.cyberbets.dao.impl.transactional.AccountManager;
import by.epam.jwd.cyberbets.dao.impl.transactional.BetManager;
import by.epam.jwd.cyberbets.dao.impl.transactional.EventManager;

public enum DaoProvider {
    INSTANCE;

    private final AccountDao accountDao = new AccountDaoImpl();
    private final BetDao betDao = new BetDaoImpl();
    private final EventDao eventDao = new EventDaoImpl();
    private final EventResultDao eventResultDao = new EventResultDaoImpl();
    private final ResourceDao resourceDao = new ResourceDaoImpl();
    private final TeamDao teamDao = new TeamDaoImpl();
    private final LeagueDao leagueDao = new LeagueDaoImpl();
    private final TransactionDao transactionDao = new TransactionDaoImpl();

    // Transactional modules
    private final EventManager eventManager = new EventManager();
    private final BetManager betManager = new BetManager();
    private final AccountManager accountManager = new AccountManager();


    public AccountDao getAccountDao() {
        return accountDao;
    }

    public BetDao getBetDao() {
        return betDao;
    }

    public EventDao getEventDao() {
        return eventDao;
    }

    public EventResultDao getEventResultDao() {
        return eventResultDao;
    }

    public LeagueDao getLeagueDao() {
        return leagueDao;
    }

    public TeamDao getTeamDao() {
        return teamDao;
    }

    public ResourceDao getResourceDao() {
        return resourceDao;
    }

    public TransactionDao getTransactionDao() {
        return transactionDao;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public BetManager getBetManager() {
        return betManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }
}
