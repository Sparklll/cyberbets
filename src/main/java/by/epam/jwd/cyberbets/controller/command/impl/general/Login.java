package by.epam.jwd.cyberbets.controller.command.impl.general;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.dto.LoginDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class Login implements Action {
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);
        String email = (String) jsonMap.get(EMAIL_PARAM);
        String password = (String) jsonMap.get(PASSWORD_PARAM);
        LoginDto loginDto = new LoginDto(email, password);

        // validate
        // make session attr
        // send response
    }
}
