package by.epam.jwd.exception;

public class ConnectionPoolException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConnectionPoolException() {
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
