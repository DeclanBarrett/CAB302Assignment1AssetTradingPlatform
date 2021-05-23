package Controllers.BackEnd.Processing;

import Controllers.Exceptions.LoginException;
import Models.InformationGrabber;

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
    public String compareLogin(String username, String hashedPassword) throws LoginException {

        String newToken = "";
        try {
            if (hashedPassword.equals(database.getPassword(username)))
            return newToken;
        } catch (Exception e) {
            throw new LoginException("Incorrect Username of Password");
        }
        throw new LoginException("Incorrect Username of Password");
    }
}
