package by.epam.jwd.cyberbets.controller.command;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public enum ActionEnum {
    DEFAULT(FORWARD_ERROR_PAGE, ::new);

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
