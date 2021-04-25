package by.epam.jwd.cyberbets.controller.command.impl.page;

import by.epam.jwd.cyberbets.controller.command.Action;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static by.epam.jwd.cyberbets.controller.Parameters.WITHDRAWAL_PAGE;

public final class WithdrawalPage implements Action {
    private static final Logger logger = LoggerFactory.getLogger(WithdrawalPage.class);

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(WITHDRAWAL_PAGE);
        requestDispatcher.forward(request, response);
    }
}
