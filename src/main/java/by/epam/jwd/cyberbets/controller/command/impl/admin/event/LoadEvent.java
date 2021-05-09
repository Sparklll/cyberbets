package by.epam.jwd.cyberbets.controller.command.impl.admin.event;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import by.epam.jwd.cyberbets.util.gson.InstantAdapter;
import com.google.gson.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.epam.jwd.cyberbets.controller.Parameters.*;
import static by.epam.jwd.cyberbets.controller.Parameters.STATUS_EXCEPTION;

public final class LoadEvent implements Action {
    private static final Logger logger = LoggerFactory.getLogger(LoadEvent.class);

    private final EventService eventService = ServiceProvider.INSTANCE.getEventService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = (Role) request.getAttribute(ROLE_ATTR);

        if (role == Role.ADMIN) {
            Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

            PrintWriter out = response.getWriter();
            JsonObject jsonResponse = new JsonObject();
            response.setContentType(JSON_UTF8_CONTENT_TYPE);

            try {
                Object filterIdObj = jsonMap.get(ID_PARAM);
                Object filterDisciplineIdObj = jsonMap.get(DISCIPLINE_PARAM);
                Object filterEventFormatObj = jsonMap.get(EVENT_FORMAT_PARAM);
                Object filterRoyaltyObj = jsonMap.get(ROYALTY_PERCENTAGE_PARAM);
                Object filterStatusObj = jsonMap.get(STATUS_PARAM);
                Object filterEventObj = jsonMap.get(EVENT_PARAM);
                Object filterLeagueNameObj = jsonMap.get(LEAGUE_NAME_PARAM);

                if ((filterIdObj instanceof Double || filterIdObj == null)
                        && (filterDisciplineIdObj instanceof Double || filterDisciplineIdObj == null)
                        && (filterEventFormatObj instanceof Double || filterEventFormatObj == null)
                        && (filterRoyaltyObj instanceof Double || filterRoyaltyObj == null)
                        && (filterStatusObj instanceof Double || filterStatusObj == null)
                        && (filterEventObj instanceof String || filterEventObj == null)
                        && (filterLeagueNameObj instanceof String || filterEventObj == null)) {

                    Double filterId = (Double) filterIdObj;
                    Double filterDisciplineId = (Double) filterDisciplineIdObj;
                    Double filterEventFormat = (Double) filterEventFormatObj;
                    Double filterRoyalty = (Double) filterRoyaltyObj;
                    Double filterStatus = (Double) filterStatusObj;
                    String filterEvent = (String) filterEventObj;
                    String filterLeagueName = (String) filterLeagueNameObj;

                    List<Event> events = eventService.findAll();
                    events = events.stream()
                            .filter(e -> {
                                String eventInfo = (e.getFirstTeam().getName() + e.getSecondTeam().getName()).toLowerCase();
                                return (filterId == null || e.getId() == filterId.intValue())
                                        && (filterRoyalty == null || e.getRoyaltyPercentage().compareTo(BigDecimal.valueOf(filterRoyalty)) == 0)
                                        && (StringUtils.isBlank(filterEvent) || eventInfo.contains(filterEvent.toLowerCase()))
                                        && (StringUtils.isBlank(filterLeagueName) || e.getLeague().getName().toLowerCase().contains(filterLeagueName.toLowerCase()))
                                        && (filterEventFormat == null
                                        || filterEventFormat.intValue() == 0
                                        || e.getEventFormat().getId() == filterEventFormat.intValue())
                                        && (filterDisciplineId == null
                                        || filterDisciplineId.intValue() == 0
                                        || e.getDiscipline().getId() == filterDisciplineId.intValue())
                                        && (filterStatus == null
                                        || filterStatus.intValue() == 0
                                        || e.getStatus().getId() == filterStatus.intValue());
                            })
                            .sorted(Comparator.comparing(Event::getStartDate))
                            .collect(Collectors.toList());

                    Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
                    String jsonStrEvents = gson.toJson(events);
                    JsonElement jsonElementEvents = JsonParser.parseString(jsonStrEvents);

                    jsonResponse.add(DATA_PROPERTY, jsonElementEvents);
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
                }
            } catch (ServiceException e) {
                jsonResponse.addProperty(STATUS_PARAM, STATUS_EXCEPTION);
                logger.error(e.getMessage(), e);
            }
            out.write(jsonResponse.toString());
        }
    }
}
