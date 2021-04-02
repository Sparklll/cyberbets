package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.*;
import by.epam.jwd.cyberbets.dao.impl.*;

public enum DaoProvider {
    INSTANCE;

    private final AccountDao accountDao = new AccountDaoImpl();
    private final BetDao betDao = new BetDaoImpl();
    private final EventDao eventDao = new EventDaoImpl();
    private final EventOutcomeTypeDao eventOutcomeTypeDao = new EventOutcomeTypeDaoImpl();
    private final EventResultDao eventResultDao = new EventResultDaoImpl();
    private final ResourceDao resourceDao = new ResourceDaoImpl();
    private final TeamDao teamDao = new TeamDaoImpl();
    private final LeagueDao leagueDao = new LeagueDaoImpl();
    private final TransactionDao transactionDao = new TransactionDaoImpl();


    public AccountDao getAccountDao() {
        return accountDao;
    }

    public BetDao getBetDao() {
        return betDao;
    }

    public EventDao getEventDao() {
        return eventDao;
    }

    public EventOutcomeTypeDao getEventOutcomeTypeDao() {
        return eventOutcomeTypeDao;
    }

    public EventResultDao getEventResultDao() {
        return eventResultDao;
    }

    public ResourceDao getResourceDao() {
        return resourceDao;
    }

    public TeamDao getTeamDao() {
        return teamDao;
    }

    public LeagueDao getLeagueDao() {
        return leagueDao;
    }

    public TransactionDao getTransactionDao() {
        return transactionDao;
    }
}
