package by.epam.jwd.cyberbets.controller.command.impl.general;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.validator.RegisterValidator;
import by.epam.jwd.cyberbets.controller.validator.ValidatorProvider;
import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.dto.RegisterDto;
import by.epam.jwd.cyberbets.service.AccountService;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class Register implements Action {
    private static final Logger logger = LoggerFactory.getLogger(Register.class);

    private final AccountService accountService = ServiceProvider.INSTANCE.getAccountService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();
        response.setContentType(JSON_UTF8_CONTENT_TYPE);

        try {
            Object emailObj = jsonMap.get(EMAIL_PARAM);
            Object passwordObj = jsonMap.get(PASSWORD_PARAM);
            Object repeatedPasswordObj = jsonMap.get(REPEATED_PASSWORD_PARAM);

            if (emailObj instanceof String email
                    && passwordObj instanceof String password
                    && repeatedPasswordObj instanceof String repeatedPassword) {

                RegisterDto registerDto = new RegisterDto(email, password, repeatedPassword);
                RegisterValidator registerValidator = ValidatorProvider.INSTANCE.getRegisterValidator();
                if (registerValidator.isValid(registerDto)) {
                    Optional<Account> existingAccount = accountService.findAccountByEmail(email);
                    if (existingAccount.isEmpty()) {
                        accountService.createAccount(registerDto);
                        HttpSession httpSession = request.getSession();
                        httpSession.setAttribute(ACCOUNT_EMAIL_ATTR, email);
                        jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
                    } else {
                        jsonResponse.addProperty(STATUS_PARAM, STATUS_DENY);
                    }
                }
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