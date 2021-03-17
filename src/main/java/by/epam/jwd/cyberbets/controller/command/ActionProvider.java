package by.epam.jwd.cyberbets.controller.command;

import by.epam.jwd.cyberbets.controller.command.impl.general.ForwardErrorPage;
import by.epam.jwd.cyberbets.controller.command.impl.general.Ignore;

import java.util.HashMap;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;


public enum ActionProvider {
    INSTANCE;

    private final Map<String, Action> actions = new HashMap<>();

    private ActionProvider() {
        actions.put(IGNORE_ACTION, new Ignore());
        actions.put(FORWARD_ERROR_PAGE_ACTION, new ForwardErrorPage());
    }

    public Action getAction(String actionName) {
        return actions.getOrDefault(actionName, actions.get(IGNORE_ACTION));
    }
}
