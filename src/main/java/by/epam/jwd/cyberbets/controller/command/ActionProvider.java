package by.epam.jwd.cyberbets.controller.command;

import java.util.HashMap;
import java.util.Map;

public final class ActionProvider {
    private static final Map<String, Action> map;

    static {
        map = new HashMap<String, Action>();
        for (ActionEnum a : ActionEnum.values()) {
            map.put(a.getActionName(), a.getAction());
        }
    }

    private ActionProvider() {
    }

    public static Action getAction(String actionName) {
        return map.getOrDefault(actionName, ActionEnum.DEFAULT.getAction());
    }
}
