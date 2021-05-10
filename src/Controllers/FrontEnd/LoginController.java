package Controllers.FrontEnd;


import Controllers.Backend.NetworkObjects.LoginToken;
import Controllers.Backend.NetworkObjects.UserInfo;
import Controllers.Socket.MockSocket;
import Models.IDataSource;

/**
 * Used to check for correct login input.
 */
public class LoginController {

    private static LoginToken currentLogin;
    private static UserInfo currentUserInfo;

    public static UserInfo GetUser() {
        return currentUserInfo;
    }

    public static LoginToken GetToken() {
        return currentLogin;
    }

    public void Logout() {
        currentLogin = null;
        currentUserInfo = null;
    }

    /**
     * Global Utility for attempting to login to the server
     */
    public void AttemptLogin(String username, String password) throws LoginException{
        System.out.println("\nUsername: " + username);
        String hashPassword = ReceiveNonceAndHash(username, password);

        IDataSource dataSource = MockSocket.getInstance();

        currentLogin = dataSource.AttemptLogin(username, hashPassword);



        if (currentLogin != null) {
            currentUserInfo = null;//new UserInfo(dataSource.GetUser(username).GetUsername(), MockSocket.GetUser(username).GetAccountType(), MockSocket.GetUser(username).GetOrganisationalUnit());
            return;
        }

        throw new LoginException("Username or Password Incorrect 1");
    }


    /**
     * Recieve salt and hashed password from database to allow for user login.
     * @param username
     * @param password
     * @return
     * @throws LoginException
     */
    private String ReceiveNonceAndHash(String username, String password) throws LoginException {

        IDataSource dataSource = MockSocket.getInstance();
        String salt = dataSource.GetSalt(username);

        if (salt == null) {
            throw new LoginException("Username or Password Incorrect 2");
        }

        ClientSecurity securityManager = new ClientSecurity();

        // Generates salted and hashed password using md5 algorithm.
        //String salt = securityManager.generateSalt();
        String hashedPassword = securityManager.hashPassword(password, salt);

        System.out.println("Password: " + password + ", Salt: " + salt);
        System.out.println("Hashed Password: " + hashedPassword);

        return hashedPassword;
    }




}
