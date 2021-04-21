package by.epam.jwd.cyberbets.dao.impl;

import by.epam.jwd.cyberbets.dao.AccountDao;
import by.epam.jwd.cyberbets.dao.BetDao;
import by.epam.jwd.cyberbets.dao.EventDao;
import by.epam.jwd.cyberbets.dao.EventResultDao;

import java.sql.Connection;

public class CompositeDao {
    private final EventDao eventDao;
    private final EventResultDao eventResultDao;
    private final AccountDao accountDao;
    private final BetDao betDao;

    public CompositeDao(Builder builder) {
        this.eventDao = builder.eventDao;
        this.eventResultDao = builder.eventResultDao;
        this.accountDao = builder.accountDao;
        this.betDao = builder.betDao;
    }

    public EventDao getEventDao() {
        return eventDao;
    }

    public EventResultDao getEventResultDao() {
        return eventResultDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public BetDao getBetDao() {
        return betDao;
    }


    public static class Builder {
        private final Connection transactionConnection;

        private EventDao eventDao;
        private EventResultDao eventResultDao;
        private AccountDao accountDao;
        private BetDao betDao;

        public Builder(Connection transactionConnection) {
            this.transactionConnection = transactionConnection;
        }

        public Builder withEventDao() {
            this.eventDao = new EventDaoImpl(transactionConnection);
            return this;
        }

        public Builder withEventResultDao() {
            this.eventResultDao = new EventResultDaoImpl(transactionConnection);
            return this;
        }

        public Builder withAccountDao() {
            this.accountDao = new AccountDaoImpl(transactionConnection);
            return this;
        }

        public Builder withBetDao() {
            this.betDao = new BetDaoImpl(transactionConnection);
            return this;
        }

        public CompositeDao build() {
            return new CompositeDao(this);
        }
    }
}
