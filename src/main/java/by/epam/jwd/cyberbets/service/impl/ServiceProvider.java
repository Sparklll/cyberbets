package by.epam.jwd.cyberbets.service.impl;

import by.epam.jwd.cyberbets.service.*;
import by.epam.jwd.cyberbets.service.impl.*;

public enum ServiceProvider {
    INSTANCE;

    private final AccountService accountService = new AccountServiceImpl();
    private final BetService betService = new BetServiceImpl();
    private final EventService eventService = new EventServiceImpl();
    private final EventResultService eventResultService = new EventResultServiceImpl();
    private final ResourceService resourceService = new ResourceServiceImpl();
    private final TeamService teamService = new TeamServiceImpl();
    private final LeagueService leagueService = new LeagueServiceImpl();
    private final TransactionService transactionService = new TransactionServiceImpl();

    public AccountService getAccountService() {
        return accountService;
    }

    public BetService getBetService() {
        return betService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public EventResultService getEventResultService() {
        return eventResultService;
    }

    public ResourceService getResourceService() {
        return resourceService;
    }

    public TeamService getTeamService() {
        return teamService;
    }

    public LeagueService getLeagueService() {
        return leagueService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }
}
