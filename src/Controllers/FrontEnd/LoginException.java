package Controllers.FrontEnd;

/**
 * Thrown when login fails, displays error text
 */
public class LoginException extends Exception {

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
