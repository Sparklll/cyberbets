package by.epam.jwd.cyberbets.dao;

import by.epam.jwd.cyberbets.dao.impl.*;

public enum DaoProvider {
    INSTANCE;

    private final AccountDao accountDao = new AccountDaoImpl();
    private final BetDao betDao = new BetDaoImpl();
    private final DisciplineDao disciplineDao = new DisciplineDaoImpl();
    private final EventDao eventDao = new EventDaoImpl();
    private final EventFormatDao eventFormatDao = new EventFormatDaoImpl();
    private final EventOutcomeTypeDao eventOutcomeTypeDao = new EventOutcomeTypeDaoImpl();
    private final EventResultDao eventResultDao = new EventResultDaoImpl();
    private final ResourceDao resourceDao = new ResourceDaoImpl();
    private final TeamDao teamDao = new TeamDaoImpl();
    private final TransactionDao transactionDao = new TransactionDaoImpl();


    public AccountDao getAccountDao() {
        return accountDao;
    }

    public BetDao getBetDao() {
        return betDao;
    }

    public DisciplineDao getDisciplineDao() {
        return disciplineDao;
    }

    public EventDao getEventDao() {
        return eventDao;
    }

    public EventFormatDao getEventFormatDao() {
        return eventFormatDao;
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

    public TransactionDao getTransactionDao() {
        return transactionDao;
    }
}
