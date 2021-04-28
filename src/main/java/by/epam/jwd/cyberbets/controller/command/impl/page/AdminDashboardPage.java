package by.epam.jwd.cyberbets.controller.command.impl.page;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.TransactionType;
import by.epam.jwd.cyberbets.service.AccountService;
import by.epam.jwd.cyberbets.service.BetService;
import by.epam.jwd.cyberbets.service.TransactionService;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import by.epam.jwd.cyberbets.service.job.LoadEventJob;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class AdminDashboardPage implements Action {
    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardPage.class);

    private static final int MONTH_DAYS = 30;

    private final AccountService accountService = ServiceProvider.INSTANCE.getAccountService();
    private final TransactionService transactionService = ServiceProvider.INSTANCE.getTransactionService();
    private final BetService betService = ServiceProvider.INSTANCE.getBetService();

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int accountsNumber = accountService.getTotalAccountsNumber();
            int monthRegistrations = accountService.getTotalAccountsNumberByPeriod(MONTH_DAYS);
            int activeEventsNumber = LoadEventJob.cachedLiveEvents.size() + LoadEventJob.cachedUpcomingEvents.size();
            BigDecimal monthDepositTransactionsAmount =
                    transactionService.getTransactionsAmountByPeriod(MONTH_DAYS, TransactionType.DEPOSIT);
            BigDecimal monthProfit = betService.getTotalProfitByPeriod(MONTH_DAYS);
            BigDecimal activeEventsBetsAmount = betService.getTotalAmountOfBetsOnActiveEvents();

            request.setAttribute(ACCOUNTS_NUMBER_ATTR, accountsNumber);
            request.setAttribute(MONTH_REGISTRATIONS_ATTR, monthRegistrations);
            request.setAttribute(ACTIVE_EVENTS_NUMBER_ATTR, activeEventsNumber);
            request.setAttribute(MONTH_DEPOSIT_TRANSACTIONS_AMOUNT_ATTR, monthDepositTransactionsAmount);
            request.setAttribute(MONTH_PROFIT_ATTR, monthProfit);
            request.setAttribute(ACTIVE_EVENTS_BETS_AMOUNT_ATTR, activeEventsBetsAmount);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ADMIN_DASHBOARD_SECTION);
        requestDispatcher.forward(request, response);
    }
}
