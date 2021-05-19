package Controllers.FrontEnd.Login;


import Controllers.Backend.NetworkObjects.LoginToken;
import Controllers.Backend.NetworkObjects.UserInfo;
import Controllers.Backend.Socket.MockSocket;
import Controllers.Exceptions.LoginException;

/**
 * Used to check for correct login input.
 */
public class LoginController
{

    public static final String LOGIN_ERROR_USERNAME_PASSWORD_2 = "Username or Password Incorrect 2";
    public static final String LOGIN_ERROR_USERNAME_PASSWORD_1 = "Username or Password Incorrect 1";
    private static LoginToken currentLogin;
    private static UserInfo currentUser;

    public static UserInfo GetUser()
    {
        return currentUser;
    }

    public static LoginToken GetToken()
    {
        return currentLogin;
    }

    /**
     * Logs the user out locally
     */
    public static void Logout()
    {
        currentLogin = null;
        currentUser = null;
    }

    /**
     * Global Utility for attempting to login to the server
     */
    public void AttemptLogin(String username, String password) throws LoginException
    {

        //Get the hashed password
        String hashPassword = generateHashedPassword(username, password);

        currentLogin = MockSocket.getInstance().AttemptLogin(username, hashPassword);

        if (isCurrentLogin()) {
            currentUser = MockSocket.getInstance().GetUser(GetToken(), username);
            return;
        }

        throw new LoginException(LOGIN_ERROR_USERNAME_PASSWORD_1);
    }

    private boolean isCurrentLogin()
    {
        return currentLogin != null;
    }


    /**
     * Generates a hashed password from the password and the user's salt
     *
     * @param username - the username for the user
     * @param password - the password to hash
     * @return the hashed password
     * @throws LoginException if a username or password is mismatched
     */
    private String generateHashedPassword(String username, String password) throws LoginException
    {

        //Get the salt for the password
        String salt = MockSocket.getInstance().GetSalt(username);

        //Make sure the salt isn't blank
        if (salt == null) {
            throw new LoginException(LOGIN_ERROR_USERNAME_PASSWORD_2);
        }
        UtilLoginSecurity securityManager = new UtilLoginSecurity();

        // Generates salted and hashed password using md5 algorithm.
        //String salt = securityManager.generateSalt();
        String hashedPassword = securityManager.hashPassword(password, salt);

        System.out.println("Password: " + password + ", Salt: " + salt);
        System.out.println("Hashed Password: " + hashedPassword);

        return hashedPassword;
    }


}
