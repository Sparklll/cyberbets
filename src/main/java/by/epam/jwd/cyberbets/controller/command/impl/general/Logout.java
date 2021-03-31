package by.epam.jwd.cyberbets.controller.command.impl.general;

import by.epam.jwd.cyberbets.controller.command.Action;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class Logout implements Action {
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();
        response.setContentType(JSON_UTF8_CONTENT_TYPE);

        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
            jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
            out.write(jsonResponse.toString());
        }
    }
}
