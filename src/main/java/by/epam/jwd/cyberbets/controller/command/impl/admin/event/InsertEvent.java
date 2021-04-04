package by.epam.jwd.cyberbets.controller.command.impl.admin.event;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.validator.Validator;
import by.epam.jwd.cyberbets.controller.validator.ValidatorProvider;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.dto.EventDto;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public class InsertEvent implements Action {
    private static final Logger logger = LoggerFactory.getLogger(InsertEvent.class);

    private final EventService eventService = ServiceProvider.INSTANCE.getEventService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = Role.getRoleByName((String) request.getAttribute(ROLE_ATTR));

        if (role.getId() == Role.ADMIN.getId()) {
            Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

            if (jsonMap != null) {
                JsonObject jsonResponse = new JsonObject();
                response.setContentType(JSON_UTF8_CONTENT_TYPE);
                PrintWriter out = response.getWriter();

                try {
                    int disciplineId = ((Double) jsonMap.get(DISCIPLINE_ID_PARAM)).intValue();
                    int leagueId = ((Double) jsonMap.get(LEAGUE_ID_PARAM)).intValue();
                    int firstTeamId = ((Double) jsonMap.get(FIRST_TEAM_ID_PARAM)).intValue();
                    int secondTeamId = ((Double) jsonMap.get(SECOND_TEAM_ID_PARAM)).intValue();
                    int formatId = ((Double) jsonMap.get(FORMAT_ID_PARAM)).intValue();
                    int eventStatusId = ((Double) jsonMap.get(STATUS_PARAM)).intValue();
                    BigDecimal royalty = new BigDecimal((Double) jsonMap.get(ROYALTY_PARAM));
                    Instant startDate = Instant.ofEpochSecond(((Double) jsonMap.get(START_DATE_PARAM)).longValue());


                    EventDto eventDto = new EventDto(disciplineId, leagueId,
                            firstTeamId, secondTeamId, formatId, royalty, startDate, eventStatusId);

                    Validator<EventDto> eventValidator = ValidatorProvider.INSTANCE.getEventValidator();
                    if (eventValidator.isValid(eventDto)) {
                        int id = eventService.createEvent(eventDto);


                        jsonResponse.addProperty(ID_PARAM, id);
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
