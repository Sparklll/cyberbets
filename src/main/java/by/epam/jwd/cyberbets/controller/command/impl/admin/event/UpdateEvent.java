package by.epam.jwd.cyberbets.controller.command.impl.admin.event;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.service.EventService;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UpdateEvent implements Action {
    private static final Logger logger = LoggerFactory.getLogger(UpdateEvent.class);

    private final EventService eventService = ServiceProvider.INSTANCE.getEventService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
