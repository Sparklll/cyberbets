package by.epam.jwd.cyberbets.controller.command.impl.general;

import by.epam.jwd.cyberbets.controller.command.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Ignore implements Action {
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
