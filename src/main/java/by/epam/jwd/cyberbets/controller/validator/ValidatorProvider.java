package by.epam.jwd.cyberbets.controller.validator;

public enum ValidatorProvider {
    INSTANCE;

    private final RegisterValidator registerValidator= new RegisterValidator();

    public RegisterValidator getRegisterValidator() {
        return registerValidator;
    }
}
