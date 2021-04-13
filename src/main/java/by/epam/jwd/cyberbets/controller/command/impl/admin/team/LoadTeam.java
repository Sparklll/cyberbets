package by.epam.jwd.cyberbets.controller.command.impl.admin.team;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.League;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.Team;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import by.epam.jwd.cyberbets.service.TeamService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
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
import static by.epam.jwd.cyberbets.controller.Parameters.DISCIPLINE_PARAM;

public final class LoadTeam implements Action {
    private static final Logger logger = LoggerFactory.getLogger(LoadTeam.class);

    private final TeamService teamService = ServiceProvider.INSTANCE.getTeamService();

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
                    String filterTeamName = (String) jsonMap.get(TEAM_NAME_PARAM);
                    Double filterId = (Double) jsonMap.get(ID_PARAM);
                    Double filterTeamRating = (Double) jsonMap.get(TEAM_RATING_PARAM);
                    Double filterDisciplineId = (Double) jsonMap.get(DISCIPLINE_PARAM);

                    List<Team> teams = teamService.findAll();
                    teams = teams.stream()
                            .filter(t -> (filterId == null || t.getId() == filterId.intValue())
                                    && (filterTeamRating == null || t.getRating() == filterTeamRating.intValue())
                                    && (StringUtils.isBlank(filterTeamName) || t.getName().toLowerCase().contains(filterTeamName.toLowerCase()))
                                    && (filterDisciplineId == null
                                    || filterDisciplineId.intValue() == 0
                                    || t.getDiscipline().getId() == filterDisciplineId.intValue()))
                            .sorted(Comparator.comparing(Team::getId))
                            .collect(Collectors.toList());

                    String jsonStrTeams = new Gson().toJson(teams);
                    JsonElement jsonElementTeams = JsonParser.parseString(jsonStrTeams);

                    jsonResponse.add(DATA_PROPERTY, jsonElementTeams);
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
