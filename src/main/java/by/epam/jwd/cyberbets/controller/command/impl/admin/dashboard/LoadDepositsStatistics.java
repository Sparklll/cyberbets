package by.epam.jwd.cyberbets.controller.command.impl.admin.dashboard;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.TransactionType;
import by.epam.jwd.cyberbets.service.TransactionService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import com.google.gson.Gson;
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

public class LoadDepositsStatistics implements Action {
    private static final Logger logger = LoggerFactory.getLogger(LoadRegistrationsStatistics.class);

    private final TransactionService transactionService = ServiceProvider.INSTANCE.getTransactionService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = (Role) request.getAttribute(ROLE_ATTR);

        if (role.getId() == Role.ADMIN.getId()) {
            Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

            if (jsonMap != null) {
                JsonObject jsonResponse = new JsonObject();
                response.setContentType(JSON_UTF8_CONTENT_TYPE);
                PrintWriter out = response.getWriter();

                try {
                    Map<String, BigDecimal> transactionsDateAndAmount =
                            transactionService.getDayTransactionsAmountByPeriod(MONTH_DAYS, TransactionType.DEPOSIT);

                    String jsonStrTransactionsDateAndAmount = new Gson().toJson(transactionsDateAndAmount);
                    jsonResponse.addProperty(DATA_PROPERTY, jsonStrTransactionsDateAndAmount);
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
                } catch (ServiceException e) {
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_EXCEPTION);
                    logger.error(e.getMessage(), e);
                }
                out.write(jsonResponse.toString());
            }
        }
    }
}
