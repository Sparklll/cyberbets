package by.epam.jwd.cyberbets.controller.command.impl.admin.event;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import by.epam.jwd.cyberbets.utils.gson.InstantAdapter;
import com.google.gson.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.epam.jwd.cyberbets.controller.Parameters.*;
import static by.epam.jwd.cyberbets.controller.Parameters.STATUS_EXCEPTION;

public class LoadEvent implements Action {
    private static final Logger logger = LoggerFactory.getLogger(LoadEvent.class);

    private final EventService eventService = ServiceProvider.INSTANCE.getEventService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = Role.getRoleByName((String) request.getAttribute(ROLE_ATTR));

        if (role.getId() == Role.ADMIN.getId()) {
            Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

            if (jsonMap != null) {
                JsonObject jsonResponse = new JsonObject();
                PrintWriter out = response.getWriter();
                response.setContentType(JSON_UTF8_CONTENT_TYPE);

                try {
                    Double filterId = (Double) jsonMap.get(ID_PARAM);
                    String filterTeamName = (String) jsonMap.get(TEAM_NAME_PARAM);
                    Double filterTeamRating = (Double) jsonMap.get(TEAM_RATING_PARAM);
                    String filterDisciplineId = (String) jsonMap.get(DISCIPLINE_PARAM);


                    List<Event> events = eventService.findAll();

//                    events = events.stream()
//                            .filter(t -> (filterId == null || t.getId() == filterId.intValue())
//                                    && (filterTeamRating == null || t.getRating() == filterTeamRating.intValue())
//                                    && (StringUtils.isBlank(filterTeamName) || t.getName().toLowerCase().contains(filterTeamName.toLowerCase()))
//                                    && (StringUtils.isBlank(filterDisciplineId)
//                                    || filterDisciplineId.equals("0")
//                                    || t.getDiscipline().getId() == Integer.parseInt(filterDisciplineId)))
//                            .sorted(Comparator.comparing(Event::getStartDate))
//                            .collect(Collectors.toList());

                    Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
                    String jsonStrEvents = gson.toJson(events);
                    JsonElement jsonElementEvents = JsonParser.parseString(jsonStrEvents);

                    jsonResponse.add(DATA_PROPERTY, jsonElementEvents);
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
                } catch (ServiceException | ClassCastException e) {
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_EXCEPTION);
                    logger.info(e.getMessage(), e);
                }
                out.write(jsonResponse.toString());
            }
        }
    }
}
