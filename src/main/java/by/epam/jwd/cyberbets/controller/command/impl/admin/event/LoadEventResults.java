package by.epam.jwd.cyberbets.controller.command.impl.admin.event;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.service.EventResultService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import com.google.gson.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;
import static by.epam.jwd.cyberbets.controller.Parameters.STATUS_EXCEPTION;

public final class LoadEventResults implements Action {
    private static final Logger logger = LoggerFactory.getLogger(LoadEventResults.class);

    private final EventResultService eventResultService = ServiceProvider.INSTANCE.getEventResultService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = (Role) request.getAttribute(ROLE_ATTR);

        if (role.getId() == Role.ADMIN.getId()) {
            Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

            if (jsonMap != null) {
                JsonObject jsonResponse = new JsonObject();
                response.setContentType(JSON_UTF8_CONTENT_TYPE);
                PrintWriter out = response.getWriter();

                try {
                    Double eventId = (Double) jsonMap.get(ID_PARAM);
                    if (eventId != null) {
                        List<EventResult> eventResults = eventResultService.findAllByEventId(eventId.intValue());
                        eventResults.sort(Comparator.comparing(EventResult::getId));
                        JsonElement eventResultsElement = new Gson().toJsonTree(eventResults);

                        jsonResponse.add(DATA_PROPERTY, eventResultsElement);
                        jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
                    } else {
                        jsonResponse.addProperty(STATUS_PARAM, STATUS_DENY);
                    }
                } catch (ServiceException | ClassCastException e) {
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_EXCEPTION);
                    logger.error(e.getMessage(), e);
                }
                out.write(jsonResponse.toString());
            }
        }
    }
}
