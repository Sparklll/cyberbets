package by.epam.jwd.cyberbets.controller.validator;

import by.epam.jwd.cyberbets.domain.dto.UpdatePasswordDto;
import org.apache.commons.lang3.StringUtils;

public class UpdatePasswordValidator implements Validator<UpdatePasswordDto> {
    private static final int MIN_PASSWORD_LENGTH = 8;

    @Override
    public boolean isValid(UpdatePasswordDto updatePasswordDto) {
        return StringUtils.isNotBlank(updatePasswordDto.currentPassword())
                && StringUtils.isNotBlank(updatePasswordDto.newPassword())
                && StringUtils.isNotBlank(updatePasswordDto.repeatedNewPassword())
                && isPasswordsValid(updatePasswordDto.newPassword(), updatePasswordDto.repeatedNewPassword());
    }

    private boolean isPasswordsValid(String password, String repeatedPassword) {
        return StringUtils.isNotBlank(password)
                && password.length() >= MIN_PASSWORD_LENGTH
                && password.equals(repeatedPassword);
    }
}
