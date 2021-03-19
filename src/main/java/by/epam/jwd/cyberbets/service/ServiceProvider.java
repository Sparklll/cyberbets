package by.epam.jwd.cyberbets.service;

import by.epam.jwd.cyberbets.service.impl.*;

public enum ServiceProvider {
    INSTANCE;

    private final AccountService accountService = new AccountServiceImpl();
    private final BetService betService = new BetServiceImpl();
    private final DisciplineService disciplineDisciplineService = new DisciplineServiceImpl();
    private final EventService eventService = new EventServiceImpl();
    private final EventFormatService eventFormatService = new EventFormatServiceImpl();
    private final EventOutcomeTypeService eventOutcomeTypeService = new EventOutcomeTypeServiceImpl();
    private final EventResultService eventResultService = new EventResultServiceImpl();
    private final ResourceService resourceService = new ResourceServiceImpl();
    private final ResultStatusService resultStatusService = new ResultStatusServiceImpl();
    private final RoleService roleService = new RoleServiceImpl();
    private final TeamService teamService = new TeamServiceImpl();
    private final TransactionService transactionService = new TransactionServiceImpl();
    private final TransactionTypeService transactionTypeService = new TransactionTypeServiceImpl();

    public AccountService getAccountService() {
        return accountService;
    }

    public BetService getBetService() {
        return betService;
    }

    public DisciplineService getDisciplineDisciplineService() {
        return disciplineDisciplineService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public EventFormatService getEventFormatService() {
        return eventFormatService;
    }

    public EventOutcomeTypeService getEventOutcomeTypeService() {
        return eventOutcomeTypeService;
    }

    public EventResultService getEventResultService() {
        return eventResultService;
    }

    public ResourceService getResourceService() {
        return resourceService;
    }

    public ResultStatusService getResultStatusService() {
        return resultStatusService;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public TeamService getTeamService() {
        return teamService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public TransactionTypeService getTransactionTypeService() {
        return transactionTypeService;
    }
}
