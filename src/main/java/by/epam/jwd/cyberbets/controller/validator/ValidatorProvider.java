package by.epam.jwd.cyberbets.controller.validator;

public enum ValidatorProvider {
    INSTANCE;

    private final RegisterValidator registerValidator= new RegisterValidator();
    private final TeamValidator teamValidator = new TeamValidator();
    private final LeagueValidator leagueValidator = new LeagueValidator();

    public RegisterValidator getRegisterValidator() {
        return registerValidator;
    }

    public TeamValidator getTeamValidator() {
        return teamValidator;
    }

    public LeagueValidator getLeagueValidator() {
        return leagueValidator;
    }
}
