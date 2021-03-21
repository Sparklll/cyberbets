package by.epam.jwd.cyberbets.controller.command.impl.general;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.validator.RegisterValidator;
import by.epam.jwd.cyberbets.controller.validator.ValidatorProvider;
import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.dto.RegisterDto;
import by.epam.jwd.cyberbets.service.AccountService;
import by.epam.jwd.cyberbets.service.ServiceProvider;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        if(jsonMap != null) {
            String email = (String) jsonMap.get(EMAIL_PARAM);
            String password = (String) jsonMap.get(PASSWORD_PARAM);
            String repeatedPassword = (String) jsonMap.get(REPEATED_PASSWORD_PARAM);
            RegisterDto registerDto = new RegisterDto(email, password, repeatedPassword);

            RegisterValidator registerValidator = ValidatorProvider.INSTANCE.getRegisterValidator();
            if(registerValidator.isValid(registerDto)) {
                JsonObject jsonResponse = new JsonObject();
                PrintWriter out = response.getWriter();
                response.setContentType(JSON_CONTENT_TYPE);
                response.setCharacterEncoding(UTF8_CHARSET);

                try {
                    Optional<Account> existingAccount = accountService.findAccountByEmail(email);
                    if(existingAccount.isEmpty()) {
                        accountService.createAccount(registerDto);
                        // добавить сессию и т.д
                        jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
                    } else {
                        jsonResponse.addProperty(STATUS_PARAM, STATUS_DENY);
                    }
                    out.write(jsonResponse.toString());
                } catch (ServiceException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}