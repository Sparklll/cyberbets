package by.epam.jwd.cyberbets.controller.command.impl.admin.team;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.validator.Validator;
import by.epam.jwd.cyberbets.controller.validator.ValidatorProvider;
import by.epam.jwd.cyberbets.domain.Discipline;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.Team;
import by.epam.jwd.cyberbets.domain.dto.TeamDto;
import by.epam.jwd.cyberbets.service.ServiceProvider;
import by.epam.jwd.cyberbets.service.TeamService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
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

                String filterTeamName = (String) jsonMap.get(TEAM_NAME_PARAM);
                Double filterId = (Double) jsonMap.get(ID_PARAM);
                Double filterTeamRating = (Double) jsonMap.get(TEAM_RATING_PARAM);
                String filterDisciplineId = (String) jsonMap.get(DISCIPLINE_PARAM);

                PrintWriter out = response.getWriter();
                response.setContentType(JSON_UTF8_CONTENT_TYPE);

                try {
                    List<Team> teams = teamService.findAll();
                    teams = teams.stream()
                            .filter(t -> (filterId == null || t.getId() == filterId.intValue())
                                    && (filterTeamRating == null || t.getRating() == filterTeamRating.intValue())
                                    && (StringUtils.isBlank(filterTeamName) || t.getName().toLowerCase().contains(filterTeamName.toLowerCase()))
                                    && (StringUtils.isBlank(filterDisciplineId)
                                    || filterDisciplineId.equals("0")
                                    || t.getDiscipline().getId() == Integer.parseInt(filterDisciplineId)))
                            .collect(Collectors.toList());

                    String jsonString = new Gson().toJson(teams);
                    out.write(jsonString);
                } catch (ServiceException e) {
                    logger.info(e.getMessage(), e);
                }
            }
        }
    }
}
