package by.epam.jwd.cyberbets.controller.command.impl.admin.league;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.validator.Validator;
import by.epam.jwd.cyberbets.controller.validator.ValidatorProvider;
import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.domain.dto.LeagueDto;
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

public final class InsertLeague implements Action {
    private static final Logger logger = LoggerFactory.getLogger(InsertLeague.class);

    private final LeagueService leagueService = ServiceProvider.INSTANCE.getLeagueService();

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
                    Double disciplineId = (Double) jsonMap.get(DISCIPLINE_PARAM);
                    String leagueName = (String) jsonMap.get(LEAGUE_NAME_PARAM);
                    LinkedTreeMap<String, String> leagueIconObj = (LinkedTreeMap<String, String>) jsonMap.get(LEAGUE_ICON_PARAM);

                    if(leagueName != null
                            && leagueIconObj !=null
                            && disciplineId !=null) {

                        String leagueIconBase64 = leagueIconObj.get(PATH_PARAM);
                        LeagueDto leagueDto = new LeagueDto(leagueName, leagueIconBase64, disciplineId.intValue());

                        Validator<LeagueDto> leagueValidator = ValidatorProvider.INSTANCE.getLeagueValidator();
                        if (leagueValidator.isValid(leagueDto)) {
                            int id = leagueService.createLeague(leagueDto);

                            String leagueIconPath = "";
                            Optional<Resource> resourceOptional = leagueService.findIconResourceByLeagueId(id);
                            if(resourceOptional.isPresent()) {
                                leagueIconPath += resourceOptional.get().getPath();
                            }

                            jsonResponse.addProperty(ID_PARAM, id);
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
