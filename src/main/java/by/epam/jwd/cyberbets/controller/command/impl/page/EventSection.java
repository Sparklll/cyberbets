package by.epam.jwd.cyberbets.controller.command.impl.page;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventStatus;
import by.epam.jwd.cyberbets.domain.dto.EventResultCoefficientsDto;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class EventSection implements Action {
    private static final Logger logger = LoggerFactory.getLogger(EventSection.class);

    private final EventService eventService = ServiceProvider.INSTANCE.getEventService();

    public class EventData {
        private final Event event;
        private final EventResultCoefficientsDto totalCoefficients;

        public EventData(Event event, EventResultCoefficientsDto totalCoefficients) {
            this.event = event;
            this.totalCoefficients = totalCoefficients;
        }

        public Event getEvent() {
            return event;
        }

        public EventResultCoefficientsDto getTotalCoefficients() {
            return totalCoefficients;
        }
    }

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = null;
        try {
            // Взять куку и фильтрануть по куке
            Cookie[] cookies =  request.getCookies();

            List<EventData> liveEvents = new ArrayList<>();
            List<EventData> upcomingEvents = new ArrayList<>();
            List<EventData> pastEvents = new ArrayList<>();

            eventService.findAllEventsByStatus(EventStatus.LIVE).stream()
                   // .filter(e -> )
                    .sorted(Comparator.comparing(Event::getStartDate))
                    .forEach(e -> {

                        EventResultCoefficientsDto totalCoefficients = null;
                        liveEvents.add(new EventData(e, totalCoefficients));
                    });

            eventService.findAllEventsByStatus(EventStatus.PENDING).stream()
                 //   .filter(e -> )
                    .sorted(Comparator.comparing(Event::getStartDate))
                    .forEach(e -> {
                        EventResultCoefficientsDto totalCoefficients = null;
                        upcomingEvents.add(new EventData(e, totalCoefficients));
                    });
            ;
            eventService.findAllEventsByStatus(EventStatus.FINISHED, 30).stream()
                  //  .filter(e-> )
                    .sorted(Comparator.comparing(Event::getStartDate))
                    .forEach(e -> {
                        EventResultCoefficientsDto totalCoefficients = null;
                        pastEvents.add(new EventData(e, totalCoefficients));
                    });

            request.setAttribute(LIVE_EVENTS_ATTR, liveEvents);
            request.setAttribute(UPCOMING_EVENTS_ATTR, upcomingEvents);
            request.setAttribute(PAST_EVENTS_ATTR, pastEvents);
            requestDispatcher = request.getRequestDispatcher(EVENT_SECTION);
            requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
