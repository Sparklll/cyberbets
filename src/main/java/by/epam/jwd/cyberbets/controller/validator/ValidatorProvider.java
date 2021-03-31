package by.epam.jwd.cyberbets.controller.validator;

public enum ValidatorProvider {
    INSTANCE;

    private final RegisterValidator registerValidator= new RegisterValidator();
    private final TeamValidator teamValidator = new TeamValidator();

    public RegisterValidator getRegisterValidator() {
        return registerValidator;
    }

    public TeamValidator getTeamValidator() {
        return teamValidator;
    }
}
