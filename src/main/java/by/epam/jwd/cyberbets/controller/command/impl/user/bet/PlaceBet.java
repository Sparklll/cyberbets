package by.epam.jwd.cyberbets.controller.command.impl.user.bet;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class PlaceBet implements Action {
    private static final Logger logger = LoggerFactory.getLogger(PlaceBet.class);

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = Role.getRoleByName((String) request.getAttribute(ROLE_ATTR));

        if (role.getId() >= Role.USER.getId()) {
            Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

            Double eventResultId = (Double) jsonMap.get(ID_PARAM);
            Double amount = (Double) jsonMap.get(AMOUNT_PARAM);

            if(eventResultId != null && amount != null) {

            }
        }
    }
}
