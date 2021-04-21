package by.epam.jwd.cyberbets.controller.command.impl.admin.team;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.validator.Validator;
import by.epam.jwd.cyberbets.controller.validator.ValidatorProvider;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.dto.TeamDto;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import by.epam.jwd.cyberbets.service.TeamService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class InsertTeam implements Action {
    private static final Logger logger = LoggerFactory.getLogger(InsertTeam.class);

    private final TeamService teamService = ServiceProvider.INSTANCE.getTeamService();

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
                    Double teamRating = (Double) jsonMap.get(TEAM_RATING_PARAM);
                    Double disciplineId = (Double) jsonMap.get(DISCIPLINE_PARAM);
                    String teamName = (String) jsonMap.get(TEAM_NAME_PARAM);
                    LinkedTreeMap<String, String> teamLogoObj = (LinkedTreeMap<String, String>) jsonMap.get(TEAM_LOGO_PARAM);

                    if (teamRating != null
                            && disciplineId != null
                            && teamName != null
                            && teamLogoObj != null) {

                        String teamLogoBase64 = teamLogoObj.get(PATH_PARAM);
                        TeamDto teamDto = new TeamDto(teamName, teamRating.intValue(), teamLogoBase64,
                                disciplineId.intValue());

                        Validator<TeamDto> teamValidator = ValidatorProvider.INSTANCE.getTeamValidator();
                        if (teamValidator.isValid(teamDto)) {
                            int id = teamService.createTeam(teamDto);

                            String teamLogoPath = "";
                            Optional<Resource> resourceOptional = teamService.findLogoResourceByTeamId(id);
                            if (resourceOptional.isPresent()) {
                                teamLogoPath += resourceOptional.get().getPath();
                            }

                            jsonResponse.addProperty(ID_PARAM, id);
                            jsonResponse.addProperty(PATH_PARAM, teamLogoPath);
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
