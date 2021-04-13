package by.epam.jwd.cyberbets.controller.command.impl.general;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.dto.LoginDto;
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

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class Login implements Action {
    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    private final AccountService accountService = ServiceProvider.INSTANCE.getAccountService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

        if (jsonMap != null) {
            String email = (String) jsonMap.get(EMAIL_PARAM);
            String password = (String) jsonMap.get(PASSWORD_PARAM);
            LoginDto loginDto = new LoginDto(email, password);

            PrintWriter out = response.getWriter();
            JsonObject jsonResponse = new JsonObject();
            response.setContentType(JSON_UTF8_CONTENT_TYPE);
            try {
                if (accountService.isAuthorizationValid(loginDto)) {
                    HttpSession httpSession = request.getSession();
                    httpSession.setAttribute(ACCOUNT_EMAIL_ATTR, email);
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
