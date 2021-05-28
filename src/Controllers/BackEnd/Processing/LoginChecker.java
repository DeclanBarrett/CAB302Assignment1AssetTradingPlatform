package Controllers.BackEnd.Processing;

import Controllers.Exceptions.AuthenticationException;
import Models.InformationGrabber;

/**
 * Authenticates login requests and ensure correct values are entered.
 */
public class LoginChecker {

    InformationGrabber database;

    public LoginChecker(InformationGrabber database) {
        this.database = database;
    }

    /**
     * Used to authenticate user login requests
     * @param username username for the user to login
     * @param hashedPassword password to check the database against
     * @return a JWT token
     */
    public String compareLogin(String username, String hashedPassword) throws AuthenticationException {
        try {
            if (hashedPassword.equals(database.getPassword(username))) {
                JWTHandler jwtHandler = new JWTHandler();
                String token = jwtHandler.createToken(username);
                return token;
            }
        } catch (Exception e) {
            throw new AuthenticationException("Incorrect Username of Password");
        }
        throw new AuthenticationException("Incorrect Username of Password");

    }
}
