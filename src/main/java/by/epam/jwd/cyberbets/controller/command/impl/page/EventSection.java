package by.epam.jwd.cyberbets.controller.command.impl.page;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventOutcomeType;
import by.epam.jwd.cyberbets.domain.dto.CoefficientsDto;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import by.epam.jwd.cyberbets.service.job.LoadCoefficientsJob;
import by.epam.jwd.cyberbets.service.job.LoadEventJob;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class EventSection implements Action {
    private static final Logger logger = LoggerFactory.getLogger(EventSection.class);

    private static final String VERTICAL_BAR_PATTERN = "\\|";

    private final EventService eventService = ServiceProvider.INSTANCE.getEventService();
    private final HashMap<Integer, String> disciplineMap = new HashMap<>();

    {
        disciplineMap.put(1, CSGO_DISC);
        disciplineMap.put(2, DOTA2_DISC);
        disciplineMap.put(3, LOL_DISC);
        disciplineMap.put(4, VALORANT_DISC);
    }

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Cookie langCookie = null;
        Cookie disciplineFilterCookie = null;

        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            if (cookieName.equals(LANG_COOKIE)) {
                langCookie = cookie;
            }
            if (cookieName.equals(DISCIPLINE_FILTER_COOKIE)) {
                disciplineFilterCookie = cookie;
            }
        }
        Locale locale = langCookie != null
                ? new Locale(langCookie.getValue())
                : Locale.getDefault();

        List<String> filterDisciplines = null;
        if (disciplineFilterCookie != null) {
            filterDisciplines = Arrays.stream(disciplineFilterCookie.getValue().split(VERTICAL_BAR_PATTERN)).toList();
        }

        Map<Integer, List<CoefficientsDto>> coefficients = LoadCoefficientsJob.cachedCoefficients;

        List<EventData> liveEvents = new ArrayList<>();
        List<EventData> upcomingEvents = new ArrayList<>();
        List<EventData> pastEvents = new ArrayList<>();

        boolean isFilterDisciplineEmpty = filterDisciplines == null;
        List<String> finalFilterDisciplines = filterDisciplines;
        LoadEventJob.cachedLiveEvents.stream()
                .filter(e -> isFilterDisciplineEmpty
                        || finalFilterDisciplines.contains(disciplineMap.get(e.getDiscipline().getId())))
                        .sorted(Comparator.comparing(Event::getStartDate))
                        .forEach(e -> {
                            List<CoefficientsDto> eventCoefficients = coefficients.get(e.getId());
                            for (CoefficientsDto coefficientsDto : eventCoefficients) {
                                if (coefficientsDto.getEventOutcomeTypeId() == EventOutcomeType.TOTAL_WINNER.getId()) {
                                    liveEvents.add(new EventData(e, coefficientsDto));
                                    break;
                                }
                            }
                        });
        LoadEventJob.cachedUpcomingEvents.stream()
                .filter(e -> isFilterDisciplineEmpty
                        || finalFilterDisciplines.contains(disciplineMap.get(e.getDiscipline().getId())))
                .sorted(Comparator.comparing(Event::getStartDate))
                .forEach(e -> {
                    List<CoefficientsDto> eventCoefficients = coefficients.get(e.getId());
                    for (CoefficientsDto coefficientsDto : eventCoefficients) {
                        if (coefficientsDto.getEventOutcomeTypeId() == EventOutcomeType.TOTAL_WINNER.getId()) {
                            liveEvents.add(new EventData(e, coefficientsDto));
                            break;
                        }
                    }
                });
        LoadEventJob.cachedPastEvents.stream()
                .filter(e -> isFilterDisciplineEmpty
                        || finalFilterDisciplines.contains(disciplineMap.get(e.getDiscipline().getId())))
                .sorted(Comparator.comparing(Event::getStartDate))
                .forEach(e -> {
                    List<CoefficientsDto> eventCoefficients = coefficients.get(e.getId());
                    for (CoefficientsDto coefficientsDto : eventCoefficients) {
                        if (coefficientsDto.getEventOutcomeTypeId() == EventOutcomeType.TOTAL_WINNER.getId()) {
                            liveEvents.add(new EventData(e, coefficientsDto));
                            break;
                        }
                    }
                });


        request.setAttribute(LOCALE_ATTR, locale);
        request.setAttribute(LIVE_EVENTS_ATTR, liveEvents);
        request.setAttribute(UPCOMING_EVENTS_ATTR, upcomingEvents);
        request.setAttribute(PAST_EVENTS_ATTR, pastEvents);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(EVENT_SECTION);
        requestDispatcher.forward(request, response);
    }

    public class EventData {
        private final Event event;
        private final CoefficientsDto totalCoefficients;

        public EventData(Event event, CoefficientsDto totalCoefficients) {
            this.event = event;
            this.totalCoefficients = totalCoefficients;
        }

        public Event getEvent() {
            return event;
        }

        public CoefficientsDto getTotalCoefficients() {
            return totalCoefficients;
        }
    }
}
