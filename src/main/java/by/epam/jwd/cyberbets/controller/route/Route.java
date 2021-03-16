package by.epam.jwd.cyberbets.controller.route;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.command.ActionListener;
import by.epam.jwd.cyberbets.controller.command.impl.page.*;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public enum Route {
    ACTION_LISTENER(ACTION_ROUTE, new ActionListener()),
    ROOT(ROOT_ROUTE, new GoToRootPage()),
    CONTACTS(CONTACTS_ROUTE, new GoToContactsPage()),
    NEWS(NEWS_ROUTE, new GoToNewsPage()),
    PRIVACY_POLICY(PRIVACY_POLICY_ROUTE, new GoToPrivacyPolicyPage()),
    SUPPORT(SUPPORT_ROUTE, new GoToSupportPage()),
    TEAM_RATING(TEAM_RATING_ROUTE, new GoToTeamRatingPage());

    private final String route;
    private final Action transitionAction;

    Route(String route, Action transitionAction) {
        this.route = route;
        this.transitionAction = transitionAction;
    }

    public String getRoute() {
        return route;
    }

    public Action getTransitionAction() {
        return transitionAction;
    }
}
