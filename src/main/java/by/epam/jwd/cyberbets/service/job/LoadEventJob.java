package by.epam.jwd.cyberbets.service.job;

import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventStatus;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LoadEventJob implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(LoadEventJob.class);

    private final EventService eventService = ServiceProvider.INSTANCE.getEventService();

    public static List<Event> cachedLiveEvents = new ArrayList<>();
    public static List<Event> cachedUpcomingEvents = new ArrayList<>();
    public static List<Event> cachedPastEvents = new ArrayList<>();


    @Override
    public void run() {
        try {
            List<Event> liveEvents = eventService.findAllEventsByStatus(EventStatus.LIVE);
            List<Event> upcomingEvents = eventService.findAllEventsByStatus(EventStatus.PENDING);
            List<Event> pastEvents = eventService.findAllEventsByStatus(EventStatus.FINISHED, 100);

            cachedLiveEvents = liveEvents;
            cachedUpcomingEvents = upcomingEvents;
            cachedPastEvents = pastEvents;
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
    }
}
