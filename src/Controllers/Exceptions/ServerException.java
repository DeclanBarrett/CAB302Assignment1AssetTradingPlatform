package Controllers.Exceptions;

/**
 * Exception thrown when server fails.
 */
public class ServerException extends Exception {
    /**
     * SetupServer exception message.
     * @param message
     */
    public ServerException(String message) {
        super(message);
    }


    /**
     * Handles server exceptions
     * @param message
     * @param cause
     */
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
