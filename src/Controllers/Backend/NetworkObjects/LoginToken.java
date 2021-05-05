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
     * @param username
     * @param date
     */
    public LoginToken(String username, Date date) {
        this.username = username;
        this.endDate = date;
    }

    public String GetUsername() {
        return username;
    }

    public Date GetDate() {
        return endDate;
    }

}
