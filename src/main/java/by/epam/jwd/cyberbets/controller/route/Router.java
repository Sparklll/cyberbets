package by.epam.jwd.cyberbets.controller.route;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.command.impl.ActionListener;
import by.epam.jwd.cyberbets.controller.command.ActionProvider;
import by.epam.jwd.cyberbets.controller.command.impl.page.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;


public enum Router {
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(Router.class);
    private final Map<String, Action> routes = new HashMap<>();

    Router() {
        routes.put(ACTION_LISTENER_ROUTE, new ActionListener());
        routes.put(ROOT_ROUTE, new RootPage());
        routes.put(CONTACTS_ROUTE, new ContactsPage());
        routes.put(NEWS_ROUTE, new NewsPage());
        routes.put(PRIVACY_POLICY_ROUTE, new PrivacyPolicyPage());
        routes.put(SUPPORT_ROUTE, new SupportPage());
        routes.put(TEAM_RATING_ROUTE, new TeamRatingPage());

        routes.put(DEPOSIT_ROUTE, new DepositPage());
        routes.put(MYBETS_ROUTE, new MyBetsPage());
        routes.put(ACCOUNT_SETTINGS_ROUTE, new SettingsPage());
        routes.put(TRANSACTIONS_ROUTE, new TransactionsHistoryPage());
        routes.put(WITHDRAWAL_ROUTE, new WithdrawalPage());

        routes.put(ADMIN_PANEL_ROUTE, new AdminPanelPage());
        routes.put(ADMIN_EVENTS_ROUTE, new AdminEventsSection());
        routes.put(ADMIN_LEAGUES_ROUTE, new AdminLeaguesSection());
        routes.put(ADMIN_TEAMS_ROUTE, new AdminTeamsSection());
        routes.put(ADMIN_NEWS_ROUTE, new AdminNewsSection());
        routes.put(ADMIN_ACCOUNTS_ROUTE, new AdminAccountsSection());
        routes.put(ADMIN_TRANSACTIONS_ROUTE, new AdminTransactionsSection());
        routes.put(ADMIN_SUPPORT_ROUTE, new AdminSupportSection());
        routes.put(ADMIN_LOGS_ROUTE, new AdminLogsSection());
    }

    public void resolveRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        Action transitionAction = routes.getOrDefault(requestURI,
                ActionProvider.INSTANCE.getAction(FORWARD_ERROR_PAGE_ACTION));
        transitionAction.perform(request, response);
    }
}
