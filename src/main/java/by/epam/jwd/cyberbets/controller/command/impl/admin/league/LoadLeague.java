package by.epam.jwd.cyberbets.controller.command.impl.admin.league;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.service.ServiceProvider;
import by.epam.jwd.cyberbets.service.TeamService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class LoadLeague implements Action {
    private static final Logger logger = LoggerFactory.getLogger(LoadLeague.class);
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
