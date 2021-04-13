package by.epam.jwd.cyberbets.service.job;

import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.dto.CoefficientsDto;
import by.epam.jwd.cyberbets.service.EventResultService;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoadCoefficientsJob implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(LoadCoefficientsJob.class);

    private final EventResultService eventResultService = ServiceProvider.INSTANCE.getEventResultService();

    public static Map<Integer, List<CoefficientsDto>> cachedCoefficients = new HashMap<>();

    @Override
    public void run() {
        List<Event> liveEvents = LoadEventJob.cachedLiveEvents;
        List<Event> upcomingEvents = LoadEventJob.cachedUpcomingEvents;
        List<Event> pastEvents = LoadEventJob.cachedPastEvents;

        List<Event> events = Stream.concat(Stream.concat(liveEvents.stream(), upcomingEvents.stream()), pastEvents.stream())
                .collect(Collectors.toList());

        try {
            for (Event event: events) {
                int eventId = event.getId();
                List<Integer> eventResultIds = eventResultService.findAllEventResultIdsByEventId(eventId);
                List<CoefficientsDto> coefficientsDtos = new ArrayList<>();

                for (int eventResultId: eventResultIds) {
                    Optional<CoefficientsDto> coefficientsDtoOptional =
                            eventResultService.receiveEventResultCoefficients(eventResultId);

                    coefficientsDtoOptional.ifPresent(coefficientsDtos::add);
                }
                cachedCoefficients.put(eventId, coefficientsDtos);
            }
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
    }
}
