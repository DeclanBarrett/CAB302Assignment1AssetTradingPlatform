package Controllers.Backend.NetworkObjects;

import java.io.Serializable;
import java.util.Date;

/**
 * Testing for login.
 */
public class LoginToken implements Serializable {

    private String username;
    private Date endDate;

    /**
     * Login token used to signify an existing login
     * @param username of logged in individual
     * @param date that the token expires
     */
    public LoginToken(String username, Date date) {
        this.username = username;
        this.endDate = date;
    }

    /**
     * Gathers username of the token
     * @return String for username
     */
    public String GetUsername() {
        return username;
    }

    /**
     * Gathers expiration date of the token
     * @return Date of expiration
     */
    public Date GetDate() {
        return endDate;
    }

}
