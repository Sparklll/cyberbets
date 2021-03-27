package by.epam.jwd.cyberbets.dao.connection;

import java.util.ResourceBundle;
import static by.epam.jwd.cyberbets.controller.Parameters.CONNECTION_PROPERTIES;

public enum ConnectionResourceManager {
    INSTANCE;

    private final ResourceBundle bundle = ResourceBundle.getBundle(CONNECTION_PROPERTIES);
    public String getValue(String key) {
        return bundle.getString(key);
    }
}
