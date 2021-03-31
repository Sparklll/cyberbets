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
        routes.put(ROOT_ROUTE, new ForwardRootPage());
        routes.put(CONTACTS_ROUTE, new ForwardContactsPage());
        routes.put(NEWS_ROUTE, new ForwardNewsPage());
        routes.put(PRIVACY_POLICY_ROUTE, new ForwardPrivacyPolicyPage());
        routes.put(SUPPORT_ROUTE, new ForwardSupportPage());
        routes.put(TEAM_RATING_ROUTE, new ForwardTeamRatingPage());

        routes.put(DEPOSIT_ROUTE, new ForwardDepositPage());
        routes.put(MYBETS_ROUTE, new ForwardMyBetsPage());
        routes.put(ACCOUNT_SETTINGS_ROUTE, new ForwardSettingsPage());
        routes.put(TRANSACTIONS_ROUTE, new ForwardTransactionsHistoryPage());
        routes.put(WITHDRAWAL_ROUTE, new ForwardWithdrawalPage());

        routes.put(ADMIN_PANEL_ROUTE, new ForwardAdminPanelPage());
    }

    public void resolveRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        Action transitionAction = routes.getOrDefault(requestURI,
                ActionProvider.INSTANCE.getAction(FORWARD_ERROR_PAGE_ACTION));
        transitionAction.perform(request, response);
    }
}
