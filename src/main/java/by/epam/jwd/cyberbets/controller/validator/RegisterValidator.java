package by.epam.jwd.cyberbets.controller.validator;

import by.epam.jwd.cyberbets.domain.dto.RegisterDto;

public class RegisterValidator implements Validator<RegisterDto> {

    private static final String EMAIL_REGEX = "^[-!#$%&'*+\\/0-9=?A-Z^_a-z`\\{|\\}~](\\.?[-!#$%&'*+\\/0-9=?A-Z^_a-z`\\{|\\}~])*@[a-zA-Z0-9](-*\\.?[a-zA-Z0-9])*\\.[a-zA-Z](-?[a-zA-Z0-9])+$";
    private static final int MIN_PASSWORD_LENGTH = 8;

    @Override
    public boolean isValid(RegisterDto registerDto) {
        return isEmailValid(registerDto.email())
                && isPasswordsValid(registerDto.password(), registerDto.repeatedPassword());
    }

    private boolean isEmailValid(String email) {
        return email.matches(EMAIL_REGEX);
    }

    private boolean isPasswordsValid(String password, String repeatedPassword) {
        return password.length() >= MIN_PASSWORD_LENGTH
                && password.equals(repeatedPassword);
    }
}
