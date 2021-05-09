package by.epam.jwd.cyberbets.controller.command.impl.admin.event;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.validator.EventResultValidator;
import by.epam.jwd.cyberbets.controller.validator.Validator;
import by.epam.jwd.cyberbets.controller.validator.ValidatorProvider;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.dto.EventDto;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import by.epam.jwd.cyberbets.util.gson.InstantAdapter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class InsertEvent implements Action {
    private static final Logger logger = LoggerFactory.getLogger(InsertEvent.class);

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
                Object disciplineIdObj = jsonMap.get(DISCIPLINE_PARAM);
                Object leagueIdObj = jsonMap.get(LEAGUE_ID_PARAM);
                Object firstTeamIdObj = jsonMap.get(FIRST_TEAM_ID_PARAM);
                Object secondTeamIdObj = jsonMap.get(SECOND_TEAM_ID_PARAM);
                Object formatIdObj = jsonMap.get(FORMAT_ID_PARAM);
                Object eventStatusIdObj = jsonMap.get(STATUS_PARAM);
                Object royaltyParamObj = jsonMap.get(ROYALTY_PERCENTAGE_PARAM);
                Object startDateParamObj = jsonMap.get(START_DATE_PARAM);
                Object eventResultsJsonStrObj = new Gson().toJson(jsonMap.get(EVENT_RESULTS_PARAM));

                if (disciplineIdObj instanceof Double disciplineId
                        && leagueIdObj instanceof Double leagueId
                        && firstTeamIdObj instanceof Double firstTeamId
                        && secondTeamIdObj instanceof Double secondTeamId
                        && formatIdObj instanceof Double formatId
                        && eventStatusIdObj instanceof Double eventStatusId
                        && royaltyParamObj instanceof Double royaltyParam
                        && startDateParamObj instanceof Double startDateParam
                        && eventResultsJsonStrObj instanceof String eventResultsJsonStr) {

                    BigDecimal royalty = new BigDecimal(royaltyParam);
                    Instant startDate = Instant.ofEpochSecond(startDateParam.longValue());

                    EventDto eventDto = new EventDto(disciplineId.intValue(), leagueId.intValue(),
                            firstTeamId.intValue(), secondTeamId.intValue(),
                            formatId.intValue(), royalty, startDate, eventStatusId.intValue());
                    List<EventResult> eventResults = new Gson().fromJson(eventResultsJsonStr,
                            new TypeToken<ArrayList<EventResult>>() {
                            }.getType());

                    Validator<EventDto> eventValidator = ValidatorProvider.INSTANCE.getEventValidator();
                    EventResultValidator eventResultValidator = ValidatorProvider.INSTANCE.getEventResultValidator();
                    if (eventValidator.isValid(eventDto) && eventResultValidator.isValid(eventResults)) {
                        int id = eventService.createEvent(eventDto, eventResults);
                        Event createdEvent = eventService.findEventById(id).get();

                        Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
                        String jsonStrEvent = gson.toJson(createdEvent);
                        JsonElement jsonElementEvent = JsonParser.parseString(jsonStrEvent);

                        jsonResponse.add(DATA_PROPERTY, jsonElementEvent);
                        jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
                    } else {
                        jsonResponse.addProperty(STATUS_PARAM, STATUS_DENY);
                    }
                } else {
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_DENY);
                }
            } catch (ServiceException e) {
                jsonResponse.addProperty(STATUS_PARAM, STATUS_EXCEPTION);
                logger.error(e.getMessage(), e);
            }
            out.write(jsonResponse.toString());
        }
    }
}
