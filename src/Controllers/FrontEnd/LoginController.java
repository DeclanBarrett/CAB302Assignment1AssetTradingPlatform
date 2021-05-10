package Controllers.FrontEnd;


import Controllers.Backend.NetworkObjects.LoginToken;
import Controllers.Backend.NetworkObjects.User;

import java.security.NoSuchAlgorithmException;

/**
 * Used to check for correct login input.
 */
public class LoginController {

    private static LoginToken currentLogin;
    private static User currentUser;

    public static User GetUser() {
        return currentUser;
    }

    public static LoginToken GetToken() {
        return currentLogin;
    }

    public void Logout() {
        currentLogin = null;
        currentUser = null;
    }

    /**
     * Global Utility for attempting to login to the server
     */
    public void AttemptLogin(String username, String password) throws LoginException{
        System.out.println("\nUsername: " + username);
        String hashPassword = ReceiveNonceAndHash(username, password);

        currentLogin = null;//MockSocket.Login(username, hashPassword);



        if (currentLogin != null) {
            currentUser = null;//new User(MockSocket.GetUser(username).GetUsername(), MockSocket.GetUser(username).GetAccountType(), MockSocket.GetUser(username).GetOrganisationalUnit());
            return;
        }

        throw new LoginException("Username or Password Incorrect 1");
    }




    private String ReceiveNonceAndHash(String username, String password) throws LoginException {

        String salt = null;//MockSocket.RetrieveNonce(username);



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
