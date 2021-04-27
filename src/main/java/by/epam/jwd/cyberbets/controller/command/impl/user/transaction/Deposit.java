package by.epam.jwd.cyberbets.controller.command.impl.user.transaction;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.service.AccountService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;
import static by.epam.jwd.cyberbets.controller.Parameters.AMOUNT_PARAM;

public final class Deposit implements Action {
    private static final Logger logger = LoggerFactory.getLogger(Deposit.class);

    private final AccountService accountService = ServiceProvider.INSTANCE.getAccountService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = (Role) request.getAttribute(ROLE_ATTR);

        if (role.getId() >= Role.USER.getId()) {
            Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

            if (jsonMap != null) {
                JsonObject jsonResponse = new JsonObject();
                response.setContentType(JSON_UTF8_CONTENT_TYPE);
                PrintWriter out = response.getWriter();

                try {
                    Double amount = (Double) jsonMap.get(AMOUNT_PARAM);

                    if (amount != null) {
                        // Simple stub, no payment service redirection;
                        BigDecimal amountValue = BigDecimal.valueOf(amount);
                        if(amountValue.compareTo(BigDecimal.ZERO) > 0) {
                            int accountId = (int) request.getAttribute(ACCOUNT_ID_ATTR);
                            accountService.performDeposit(accountId, amountValue);
                            jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
                        } else {
                            jsonResponse.addProperty(STATUS_PARAM, STATUS_DENY);
                        }
                    } else {
                        jsonResponse.addProperty(STATUS_PARAM, STATUS_DENY);
                    }
                } catch (ServiceException | ClassCastException e) {
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_EXCEPTION);
                    logger.error(e.getMessage(), e);
                }
                out.write(jsonResponse.toString());
            }
        }
    }
}

