package by.epam.jwd.cyberbets.controller.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Action {
    void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
