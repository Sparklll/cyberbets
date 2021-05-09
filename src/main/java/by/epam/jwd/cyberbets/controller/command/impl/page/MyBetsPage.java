package by.epam.jwd.cyberbets.controller.command.impl.page;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.Bet;
import by.epam.jwd.cyberbets.domain.Bet.Upshot;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.dto.CoefficientsDto;
import by.epam.jwd.cyberbets.service.BetService;
import by.epam.jwd.cyberbets.service.EventResultService;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class MyBetsPage implements Action {
    private static final Logger logger = LoggerFactory.getLogger(MyBetsPage.class);

    private final BetService betService = ServiceProvider.INSTANCE.getBetService();
    private final EventService eventService = ServiceProvider.INSTANCE.getEventService();
    private final EventResultService eventResultService = ServiceProvider.INSTANCE.getEventResultService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int accountId = (int) request.getAttribute(ACCOUNT_ID_ATTR);
            List<BetData> betsData = new ArrayList<>();

            List<Bet> accountBets = betService.findAllBetsByAccountId(accountId);
            for(Bet bet : accountBets) {
                int eventResultId = bet.getEventResultId();
                Optional<EventResult> eventResultOptional = eventResultService.findEventResultById(eventResultId);
                Optional<CoefficientsDto> coefficientsDtoOptional = eventResultService.receiveEventResultCoefficients(eventResultId);

                if(eventResultOptional.isPresent() && coefficientsDtoOptional.isPresent()){
                    EventResult eventResult = eventResultOptional.get();
                    CoefficientsDto coefficients = coefficientsDtoOptional.get();
                    BigDecimal betCoefficient = BigDecimal.ZERO;

                    if(bet.getUpshot() == Upshot.FIRST_UPSHOT) {
                        betCoefficient = coefficients.getFirstUpshotOdds();
                    } else if (bet.getUpshot() == Upshot.SECOND_UPSHOT) {
                        betCoefficient = coefficients.getSecondUpshotOdds();
                    }

                    int eventId = eventResult.getEventId();
                    Optional<Event> eventOptional = eventService.findEventById(eventId);

                    if(eventOptional.isPresent()) {
                        Event event = eventOptional.get();
                        BetData betData = new BetData(event, eventResult, betCoefficient, bet);
                        betsData.add(betData);
                    }
                }
            }

            request.setAttribute(BETS_DATA_ATTR, betsData);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(MYBETS_PAGE);
        requestDispatcher.forward(request, response);
    }

    public static class BetData {
        private final Event event;
        private final EventResult eventResult;
        private final BigDecimal betCoefficient;
        private final Bet bet;

        public BetData(Event event, EventResult eventResult, BigDecimal betCoefficient, Bet bet) {
            this.event = event;
            this.eventResult = eventResult;
            this.betCoefficient = betCoefficient;
            this.bet = bet;
        }

        public Event getEvent() {
            return event;
        }

        public EventResult getEventResult() {
            return eventResult;
        }

        public BigDecimal getBetCoefficient() {
            return betCoefficient;
        }

        public Bet getBet() {
            return bet;
        }
    }
}
