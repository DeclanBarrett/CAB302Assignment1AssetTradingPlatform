package Controllers.Exceptions;

/**
 * Thrown when login fails, displays error text
 */
public class AuthenticationException extends Exception {

    /**
     * Login exception message.
     * @param message
     */
    public AuthenticationException(String message) {
        super(message);
    }


    /**
     * Handles login exceptions
     * @param message
     * @param cause
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
