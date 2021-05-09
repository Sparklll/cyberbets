package by.epam.jwd.cyberbets.controller.command.impl.user.settings;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.validator.Validator;
import by.epam.jwd.cyberbets.controller.validator.ValidatorProvider;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.dto.UpdatePasswordDto;
import by.epam.jwd.cyberbets.service.AccountService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;
import static by.epam.jwd.cyberbets.controller.Parameters.STATUS_EXCEPTION;

public final class UpdateAccount implements Action {
    private static final Logger logger = LoggerFactory.getLogger(UpdateAccount.class);

    private final AccountService accountService = ServiceProvider.INSTANCE.getAccountService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = (Role) request.getAttribute(ROLE_ATTR);

        if (role.getId() >= Role.USER.getId()) {
            Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

            PrintWriter out = response.getWriter();
            JsonObject jsonResponse = new JsonObject();
            response.setContentType(JSON_UTF8_CONTENT_TYPE);

            try {
                Object newAvatarBase64Obj = jsonMap.get(NEW_AVATAR_PARAM);
                Object currentPasswordObj = jsonMap.get(CURRENT_PASSWORD_PARAM);
                Object newPasswordObj = jsonMap.get(NEW_PASSWORD_PARAM);
                Object repeatedNewPasswordObj = jsonMap.get(REPEATED_NEW_PASSWORD_PARAM);

                boolean isUpdated = false;
                int accountId = (int) request.getAttribute(ACCOUNT_ID_ATTR);

                if (currentPasswordObj instanceof String currentPassword
                        && newPasswordObj instanceof String newPassword
                        && repeatedNewPasswordObj instanceof String repeatedNewPassword) {

                    UpdatePasswordDto updatePasswordDto =
                            new UpdatePasswordDto(currentPassword, newPassword, repeatedNewPassword);
                    Validator<UpdatePasswordDto> updatePasswordDtoValidator =
                            ValidatorProvider.INSTANCE.getUpdatePasswordValidator();
                    if (updatePasswordDtoValidator.isValid(updatePasswordDto)) {
                        isUpdated = accountService.updateAccountPassword(accountId, updatePasswordDto);
                        HttpSession httpSession = request.getSession(false);
                        if (isUpdated && httpSession != null) {
                            httpSession.invalidate();
                        }
                    }
                }
                
                if (newAvatarBase64Obj instanceof String newAvatarBase64
                        && StringUtils.isNotBlank(newAvatarBase64)) {
                    isUpdated = accountService.updateAccountAvatar(accountId, newAvatarBase64);
                }

                if (isUpdated) {
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
                } else {
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_DENY);
                }
            } catch (ServiceException e) {
                jsonResponse.addProperty(STATUS_PARAM, STATUS_EXCEPTION);
                logger.error(e.getMessage(), e);
            }
            out.write(jsonResponse.toString());
        }
    }
}
