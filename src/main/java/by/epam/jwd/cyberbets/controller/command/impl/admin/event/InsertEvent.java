package by.epam.jwd.cyberbets.controller.command.impl.admin.event;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.validator.EventResultValidator;
import by.epam.jwd.cyberbets.controller.validator.Validator;
import by.epam.jwd.cyberbets.controller.validator.ValidatorProvider;
import by.epam.jwd.cyberbets.dao.impl.DaoProvider;
import by.epam.jwd.cyberbets.dao.impl.EventManager;
import by.epam.jwd.cyberbets.domain.Event;
import by.epam.jwd.cyberbets.domain.EventResult;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.dto.EventDto;
import by.epam.jwd.cyberbets.domain.dto.EventResultDto;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import by.epam.jwd.cyberbets.utils.gson.InstantAdapter;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class InsertEvent implements Action {
    private static final Logger logger = LoggerFactory.getLogger(InsertEvent.class);

    private final EventManager eventManager = DaoProvider.INSTANCE.getEventManager();

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
                    Double disciplineId = (Double) jsonMap.get(DISCIPLINE_PARAM);
                    Double leagueId = (Double) jsonMap.get(LEAGUE_ID_PARAM);
                    Double firstTeamId = (Double) jsonMap.get(FIRST_TEAM_ID_PARAM);
                    Double secondTeamId = (Double) jsonMap.get(SECOND_TEAM_ID_PARAM);
                    Double formatId = (Double) jsonMap.get(FORMAT_ID_PARAM);
                    Double eventStatusId = (Double) jsonMap.get(STATUS_PARAM);
                    Double royaltyParam = (Double) jsonMap.get(ROYALTY_PERCENTAGE_PARAM);
                    Double startDateParam = (Double) jsonMap.get(START_DATE_PARAM);
                    String eventResultsJsonStr = new Gson().toJson(jsonMap.get(EVENT_RESULTS_PARAM));

                    if(disciplineId != null
                            && leagueId != null
                            && firstTeamId != null
                            && secondTeamId != null
                            && formatId != null
                            && eventStatusId != null
                            && royaltyParam != null
                            && startDateParam != null
                            && eventResultsJsonStr != null) {

                        BigDecimal royalty = new BigDecimal(royaltyParam);
                        Instant startDate = Instant.ofEpochSecond(startDateParam.longValue());

                        EventDto eventDto = new EventDto(disciplineId.intValue(), leagueId.intValue(),
                                firstTeamId.intValue(), secondTeamId.intValue(),
                                formatId.intValue(), royalty, startDate, eventStatusId.intValue());
                        List<EventResult> eventResults = new Gson().fromJson(eventResultsJsonStr,
                                new TypeToken<ArrayList<EventResult>>(){}.getType());

                        Validator<EventDto> eventValidator = ValidatorProvider.INSTANCE.getEventValidator();
                        EventResultValidator eventResultValidator = ValidatorProvider.INSTANCE.getEventResultValidator();
                        if (eventValidator.isValid(eventDto) && eventResultValidator.isValid(eventResults)) {
                            int id = eventService.createEvent(eventDto);
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
                } catch (ServiceException | ClassCastException e) {
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_EXCEPTION);
                    logger.error(e.getMessage(), e);
                }
                out.write(jsonResponse.toString());
            }
        }
    }
}
