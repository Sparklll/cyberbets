package by.epam.jwd.cyberbets.controller.command.impl.page;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.Transaction;
import by.epam.jwd.cyberbets.service.TransactionService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class TransactionsHistoryPage implements Action {
    private static final Logger logger = LoggerFactory.getLogger(TransactionsHistoryPage.class);

    private final TransactionService transactionService = ServiceProvider.INSTANCE.getTransactionService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountId = (int) request.getAttribute(ACCOUNT_ID_ATTR);
        try {
            List<Transaction> accountTransactions = transactionService.findAllTransactionsByAccountId(accountId);

            request.setAttribute(ACCOUNT_TRANSACTIONS_ATTR, accountTransactions);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(TRANSACTIONS_PAGE);
            requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
