package by.epam.jwd.cyberbets.controller.command.impl.user.bet;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.validator.Validator;
import by.epam.jwd.cyberbets.controller.validator.ValidatorProvider;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.dto.BetDto;
import by.epam.jwd.cyberbets.service.AccountService;
import by.epam.jwd.cyberbets.service.BetService;
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
import java.util.Optional;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class UpdateBet implements Action {
    private static final Logger logger = LoggerFactory.getLogger(UpdateBet.class);

    private final BetService betService = ServiceProvider.INSTANCE.getBetService();
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
                Object eventResultIdObj = jsonMap.get(EVENT_RESULT_ID_PARAM);
                Object upshotIdObj = jsonMap.get(UPSHOT_ID_PARAM);
                Object amountObj = jsonMap.get(AMOUNT_PARAM);

                if (eventResultIdObj instanceof Double eventResultId
                        && upshotIdObj instanceof Double upshotId
                        && amountObj instanceof Double amount) {

                    int accountId = (int) request.getAttribute(ACCOUNT_ID_ATTR);
                    BigDecimal amountValue = BigDecimal.valueOf(amount);

                    BetDto betDto = new BetDto(accountId, eventResultId.intValue(), upshotId.intValue(), amountValue);
                    Validator<BetDto> betValidator = ValidatorProvider.INSTANCE.getBetValidator();

                    if (betValidator.isValid(betDto)) {
                        betService.updateBet(betDto);

                        Optional<BigDecimal> updatedBalanceOptional = accountService.getAccountBalance(accountId);
                        if (updatedBalanceOptional.isPresent()) {
                            BigDecimal updatedBalance = updatedBalanceOptional.get();
                            jsonResponse.addProperty(BALANCE_PARAM, updatedBalance);
                        }
                        jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
                    } else {
                        jsonResponse.addProperty(STATUS_PARAM, STATUS_DENY);
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
}
