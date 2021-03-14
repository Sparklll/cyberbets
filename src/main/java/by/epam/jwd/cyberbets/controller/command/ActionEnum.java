package by.epam.jwd.cyberbets.controller.command;

import by.epam.jwd.cyberbets.controller.command.impl.general.ForwardErrorPage;
import by.epam.jwd.cyberbets.controller.command.impl.general.Ignore;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public enum ActionEnum {
    DEFAULT(IGNORE_ACTION, new Ignore()),
    ERROR_PAGE(FORWARD_ERROR_PAGE_ACTION, new ForwardErrorPage());

    private final String actionName;
    private final Action action;

    ActionEnum(String actionName, Action action) {
        this.actionName = actionName;
        this.action = action;
    }

    public String getActionName() {
        return actionName;
    }

    public Action getAction() {
        return action;
    }
}
