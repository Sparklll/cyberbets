package by.epam.jwd.cyberbets.controller.command.impl.general.event;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.dto.CoefficientsDto;
import by.epam.jwd.cyberbets.service.BetService;
import by.epam.jwd.cyberbets.service.EventResultService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import by.epam.jwd.cyberbets.service.job.LoadCoefficientsJob;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public class LoadBetModal implements Action {
    private static final Logger logger = LoggerFactory.getLogger(LoadBetModal.class);

    private final EventResultService eventResultService = ServiceProvider.INSTANCE.getEventResultService();
    private final BetService betService = ServiceProvider.INSTANCE.getBetService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = Role.getRoleByName((String) request.getAttribute(ROLE_ATTR));
        logger.info("ok");
        if (role.getId() >= Role.USER.getId()) {
            Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

            if (jsonMap != null) {
                try {
                    Double eventId = (Double) jsonMap.get(ID_PARAM);

                    if(eventId != null) {
                        int accountId = (int) request.getAttribute(ACCOUNT_ID_ATTR);
                        List<EventResult> eventResults = eventResultService.findAllByEventId(eventId.intValue());
                        List<Bet> eventBets = betService.findAllBetsByAccountIdAndEventId(accountId, eventId.intValue());

                        Map<Integer, List<CoefficientsDto>> coefficients = LoadCoefficientsJob.cachedCoefficients;
                        List<CoefficientsDto> eventCoefficients = coefficients.get(eventId.intValue());

                        request.setAttribute(EVENT_RESULTS_ATTR, eventResults);
                        request.setAttribute(EVENT_BETS_ATTR, eventBets);
                        request.setAttribute(EVENT_COEFFICIENTS_ATTR, eventCoefficients);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(BET_MODAL);
                        requestDispatcher.forward(request, response);
                    }
                } catch (ServiceException | ClassCastException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}