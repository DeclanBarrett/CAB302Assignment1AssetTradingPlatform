package Controllers.FrontEnd;

import Controllers.Backend.LoginToken;
import Controllers.Backend.User;
import Models.MockSocket;
import Controllers.Backend.Security;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Used to check for correct login input.
 */
public class UtilLogin {

    private static LoginToken currentLogin;
    private static User currentUser;

    /**
     * Global Utility for attempting to login to the server
     */
    public static void AttemptLogin(String username, String password) throws LoginException, NoSuchAlgorithmException {

        String hashPassword = ReceiveNonceAndHash(username, password);

        currentLogin = MockSocket.Login(username, hashPassword);

        if (currentLogin != null) {
            currentUser = MockSocket.GetUser(username);
            return;
        }

        throw new LoginException("Username or Password Incorrect 1");
    }

    public static User GetUser() {
        return currentUser;
    }


    private static String ReceiveNonceAndHash(String username, String password) throws LoginException, NoSuchAlgorithmException {

        String nonce = MockSocket.RetrieveNonce(username);

        if (nonce == null) {
            throw new LoginException("Username or Password Incorrect 2");
        }

        // Generates salted and hashed password using md5 algorithm.
        byte[]salt = Security.getSalt();
        String hashedPassword = Security.getPassword(password, salt); //TODO actually hash and nonce;

        return hashedPassword;
    }


}
