package by.epam.jwd.cyberbets.controller.validator;

import by.epam.jwd.cyberbets.domain.EventResult;

public enum ValidatorProvider {
    INSTANCE;

    private final RegisterValidator registerValidator= new RegisterValidator();
    private final TeamValidator teamValidator = new TeamValidator();
    private final LeagueValidator leagueValidator = new LeagueValidator();
    private final EventValidator eventValidator = new EventValidator();
    private final EventResultValidator eventResultValidator = new EventResultValidator();

    public RegisterValidator getRegisterValidator() {
        return registerValidator;
    }

    public TeamValidator getTeamValidator() {
        return teamValidator;
    }

    public LeagueValidator getLeagueValidator() {
        return leagueValidator;
    }

    public EventValidator getEventValidator() {
        return eventValidator;
    }

    public EventResultValidator getEventResultValidator() {
        return eventResultValidator;
    }
}
