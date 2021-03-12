package by.epam.jwd.cyberbets.controller;

import by.epam.jwd.cyberbets.connection.ConnectionPool;
import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.command.ActionProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public class FrontController extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = request.getParameter(ACTION);
        Action action = ActionProvider.getAction(actionName);
        action.perform(request, response);
    }
}