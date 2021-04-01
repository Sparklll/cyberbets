package by.epam.jwd.cyberbets.controller.command.impl.page;

import by.epam.jwd.cyberbets.controller.command.Action;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.epam.jwd.cyberbets.controller.Parameters.ADMIN_LOGS_SECTION;

public class AdminLogsSection implements Action {
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ADMIN_LOGS_SECTION);
        requestDispatcher.forward(request, response);
    }
}
