package by.epam.jwd.cyberbets.service.job;

import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventStatus;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;

public class UpdateEventStatusJob implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(UpdateEventStatusJob.class);

    private final EventService eventService = ServiceProvider.INSTANCE.getEventService();

    @Override
    public void run() {
        try {
            List<Event> eventList = eventService.findAllEventsByStatus(EventStatus.PENDING);
            for(Event event : eventList) {
                int eventId = event.getId();
                Instant eventStartDate = event.getStartDate();
                Instant now = Instant.now();
                if(now.isAfter(eventStartDate)) {
                    eventService.updateEventStatus(eventId, EventStatus.LIVE);
                }
            }
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
    }
}
