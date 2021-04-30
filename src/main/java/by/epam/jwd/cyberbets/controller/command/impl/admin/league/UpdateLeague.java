package by.epam.jwd.cyberbets.controller.command.impl.admin.league;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.validator.Validator;
import by.epam.jwd.cyberbets.controller.validator.ValidatorProvider;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.dto.LeagueDto;
import by.epam.jwd.cyberbets.domain.dto.TeamDto;
import by.epam.jwd.cyberbets.service.LeagueService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
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
import static by.epam.jwd.cyberbets.controller.Parameters.STATUS_EXCEPTION;

public final class UpdateLeague implements Action {
    private static final Logger logger = LoggerFactory.getLogger(UpdateLeague.class);

    private final LeagueService leagueService = ServiceProvider.INSTANCE.getLeagueService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = (Role) request.getAttribute(ROLE_ATTR);

        if (role == Role.ADMIN) {
            Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

            if (jsonMap != null) {
                JsonObject jsonResponse = new JsonObject();
                response.setContentType(JSON_UTF8_CONTENT_TYPE);
                PrintWriter out = response.getWriter();

                try {
                    Double id = (Double) jsonMap.get(ID_PARAM);
                    Double disciplineId = (Double) jsonMap.get(DISCIPLINE_PARAM);
                    String leagueName = (String) jsonMap.get(LEAGUE_NAME_PARAM);
                    LinkedTreeMap<String, String> leagueIconObj = (LinkedTreeMap<String, String>) jsonMap.get(LEAGUE_ICON_PARAM);
                    String leagueIconBase64 = leagueIconObj != null ? leagueIconObj.get(PATH_PARAM) : "";

                    if(id != null
                            && disciplineId != null
                            && leagueName != null
                            && leagueIconObj != null) {

                        LeagueDto leagueDto = new LeagueDto(id.intValue(), leagueName,
                                leagueIconBase64, disciplineId.intValue());

                        Validator<LeagueDto> leagueDtoValidator = ValidatorProvider.INSTANCE.getLeagueValidator();
                        if (leagueDtoValidator.isValid(leagueDto)) {
                            leagueService.updateLeague(leagueDto);

                            String leagueIconPath = "";
                            Optional<Resource> resourceOptional = leagueService.findIconResourceByLeagueId(id.intValue());
                            if(resourceOptional.isPresent()) {
                                leagueIconPath += resourceOptional.get().getPath();
                            }

                            jsonResponse.addProperty(PATH_PARAM, leagueIconPath);
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
