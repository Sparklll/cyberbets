package by.epam.jwd.cyberbets.controller.command.impl.admin.league;

import by.epam.jwd.cyberbets.controller.command.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class DeleteLeague implements Action {
    private static final Logger logger = LoggerFactory.getLogger(DeleteLeague.class);

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
