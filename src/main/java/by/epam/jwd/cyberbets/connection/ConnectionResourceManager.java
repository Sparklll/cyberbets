package by.epam.jwd.cyberbets.connection;

import java.util.ResourceBundle;

public enum ConnectionResourceManager {
    INSTANCE;

    private final ResourceBundle bundle = ResourceBundle.getBundle("connection.db");
    public String getValue(String key) {
        return bundle.getString(key);
    }
}
