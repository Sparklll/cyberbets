package by.epam.jwd.cyberbets.controller.command.impl.page;


import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.command.ActionProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.epam.jwd.cyberbets.controller.Parameters.ROOT_PAGE;
import static by.epam.jwd.cyberbets.controller.Parameters.ACTION;

public class GoToRootPage implements Action {
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ROOT_PAGE);
        requestDispatcher.forward(request, response);

        String actionName = request.getParameter(ACTION);
        Action action = ActionProvider.getAction(actionName);
        action.perform(request, response);
    }
}
