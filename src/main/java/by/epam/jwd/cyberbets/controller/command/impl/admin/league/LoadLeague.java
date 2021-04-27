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

        if (role.getId() == Role.ADMIN.getId()) {
            Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

            if (jsonMap != null) {
                JsonObject jsonResponse = new JsonObject();
                PrintWriter out = response.getWriter();
                response.setContentType(JSON_UTF8_CONTENT_TYPE);

                try {
                    Double filterId = (Double) jsonMap.get(ID_PARAM);
                    Double filterDisciplineId = (Double) jsonMap.get(DISCIPLINE_PARAM);
                    String filterLeagueName = (String) jsonMap.get(LEAGUE_NAME_PARAM);

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
                } catch (ServiceException | ClassCastException e) {
                    jsonResponse.addProperty(STATUS_PARAM, STATUS_EXCEPTION);
                    logger.info(e.getMessage(), e);
                }
                out.write(jsonResponse.toString());
            }
        }
    }
}
