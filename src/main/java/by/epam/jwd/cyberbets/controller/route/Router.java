package by.epam.jwd.cyberbets.controller.route;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.command.ActionEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public enum Router {
    INSTANCE;

    private static final Map<String, Action> routes;

    static {
        routes = new HashMap<String, Action>();
        for (Route a : Route.values()) {
            routes.put(a.getRoute(), a.getTransitionAction());
        }
    }

    public void resolveRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        Action transitionAction = routes.getOrDefault(requestURI, ActionEnum.ERROR_PAGE.getAction());
        transitionAction.perform(request, response);
    }
}
