package by.epam.jwd.cyberbets.controller.command;

import by.epam.jwd.cyberbets.controller.command.impl.admin.event.*;
import by.epam.jwd.cyberbets.controller.command.impl.admin.league.DeleteLeague;
import by.epam.jwd.cyberbets.controller.command.impl.admin.league.InsertLeague;
import by.epam.jwd.cyberbets.controller.command.impl.admin.league.LoadLeague;
import by.epam.jwd.cyberbets.controller.command.impl.admin.league.UpdateLeague;
import by.epam.jwd.cyberbets.controller.command.impl.admin.team.DeleteTeam;
import by.epam.jwd.cyberbets.controller.command.impl.admin.team.InsertTeam;
import by.epam.jwd.cyberbets.controller.command.impl.admin.team.LoadTeam;
import by.epam.jwd.cyberbets.controller.command.impl.admin.team.UpdateTeam;
import by.epam.jwd.cyberbets.controller.command.impl.general.*;
import by.epam.jwd.cyberbets.controller.command.impl.general.event.LoadBetModal;
import by.epam.jwd.cyberbets.controller.command.impl.general.event.LoadCoefficients;
import by.epam.jwd.cyberbets.controller.command.impl.general.event.LoadEventCoefficients;
import by.epam.jwd.cyberbets.controller.command.impl.general.event.LoadEventSection;
import by.epam.jwd.cyberbets.controller.command.impl.page.ErrorPage;

import java.util.HashMap;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;


public enum ActionProvider {
    INSTANCE;

    private final Map<String, Action> actions = new HashMap<>();

    private ActionProvider() {
        actions.put(LOAD_BET_MODAL_ACTION, new LoadBetModal());
        actions.put(LOAD_COEFFICIENTS_ACTION, new LoadCoefficients());
        actions.put(LOAD_EVENT_COEFFICIENTS_ACTION, new LoadEventCoefficients());
        actions.put(LOAD_EVENT_RESULTS_ACTION, new LoadEventResults());
        actions.put(LOAD_EVENT_SECTION_ACTION, new LoadEventSection());
        actions.put(LOAD_EVENT_ACTION, new LoadEvent());
        actions.put(INSERT_EVENT_ACTION, new InsertEvent());
        actions.put(UPDATE_EVENT_ACTION, new UpdateEvent());
        actions.put(DELETE_EVENT_ACTION, new DeleteEvent());
        actions.put(LOAD_LEAGUE_ACTION, new LoadLeague());
        actions.put(INSERT_LEAGUE_ACTION, new InsertLeague());
        actions.put(UPDATE_LEAGUE_ACTION, new UpdateLeague());
        actions.put(DELETE_LEAGUE_ACTION, new DeleteLeague());
        actions.put(LOAD_TEAM_ACTION, new LoadTeam());
        actions.put(INSERT_TEAM_ACTION, new InsertTeam());
        actions.put(UPDATE_TEAM_ACTION, new UpdateTeam());
        actions.put(DELETE_TEAM_ACTION, new DeleteTeam());
        actions.put(REGISTER_ACTION, new Register());
        actions.put(LOGIN_ACTION, new Login());
        actions.put(LOGOUT_ACTION, new Logout());
        actions.put(IGNORE_ACTION, new Ignore());
        actions.put(FORWARD_ERROR_PAGE_ACTION, new ErrorPage());
    }

    public Action getAction(String actionName) {
        return actions.getOrDefault(actionName, actions.get(IGNORE_ACTION));
    }
}
