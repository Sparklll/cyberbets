package by.epam.jwd.cyberbets.controller.command.impl.page;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.service.AccountService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class SettingsPage implements Action {
    private static final Logger logger = LoggerFactory.getLogger(SettingsPage.class);

    private final AccountService accountService = ServiceProvider.INSTANCE.getAccountService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountId = (int) request.getAttribute(ACCOUNT_ID_ATTR);

        try {
            Optional<Account> accountOptional = accountService.findAccountById(accountId);
            if(accountOptional.isPresent()) {
                Account account = accountOptional.get();
                request.setAttribute(ACCOUNT_ATTR, account);
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ACCOUNT_SETTINGS_PAGE);
        requestDispatcher.forward(request, response);
    }
}
