package Controllers.Exceptions;

public class ServerException extends Exception {
    /**
     * Server exception message.
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
