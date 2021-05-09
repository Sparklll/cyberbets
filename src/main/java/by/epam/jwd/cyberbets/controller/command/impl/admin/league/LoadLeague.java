package by.epam.jwd.cyberbets.controller.command.impl.admin.league;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.League;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.service.LeagueService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.epam.jwd.cyberbets.controller.Parameters.*;
import static by.epam.jwd.cyberbets.controller.Parameters.STATUS_EXCEPTION;

public final class LoadLeague implements Action {
    private static final Logger logger = LoggerFactory.getLogger(LoadLeague.class);

    private final LeagueService leagueService = ServiceProvider.INSTANCE.getLeagueService();

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
                Object filterLeagueNameObj = jsonMap.get(LEAGUE_NAME_PARAM);

                if((filterIdObj instanceof Double || filterIdObj == null)
                    && (filterDisciplineIdObj instanceof Double || filterDisciplineIdObj == null)
                    && (filterLeagueNameObj instanceof String || filterLeagueNameObj == null)) {

                    Double filterId = (Double) filterIdObj;
                    Double filterDisciplineId = (Double) filterDisciplineIdObj;
                    String filterLeagueName = (String) filterLeagueNameObj;

                    List<League> leagues = leagueService.findAll();
                    leagues = leagues.stream()
                            .filter(l -> (filterId == null || l.getId() == filterId.intValue())
                                    && (StringUtils.isBlank(filterLeagueName) || l.getName().toLowerCase().contains(filterLeagueName.toLowerCase()))
                                    && (filterDisciplineId == null
                                    || filterDisciplineId.intValue() == 0
                                    || l.getDiscipline().getId() == filterDisciplineId.intValue()))
                            .sorted(Comparator.comparing(League::getId))
                            .collect(Collectors.toList());

                    String jsonStrLeagues = new Gson().toJson(leagues);
                    JsonElement jsonElementLeagues = JsonParser.parseString(jsonStrLeagues);

                    jsonResponse.add(DATA_PROPERTY, jsonElementLeagues);
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
