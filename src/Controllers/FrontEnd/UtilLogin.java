package Controllers.FrontEnd;

import Controllers.Backend.LoginToken;
import Controllers.Backend.User;
import Models.MockSocket;

public class UtilLogin {

    private static LoginToken currentLogin;
    private static User currentUser;

    /**
     * Global Utility for attempting to login to the server
     */
    public static void AttemptLogin(String username, String password) throws LoginException {

        String hashPassword = ReceiveNonceAndHash(username, password);

        currentLogin = MockSocket.Login(username, hashPassword);

        if (currentLogin != null) {
            currentUser = MockSocket.GetUser(username);
            return;
        }

        throw new LoginException("Username or Password Incorrect");
    }

    public static User GetUser() {
        return currentUser;
    }

    private static String ReceiveNonceAndHash(String username, String password) throws LoginException {

        String nonce = MockSocket.RetrieveNonce(username);

        if (nonce == null) {
            throw new LoginException("Username or Password Incorrect");
        }

        String hashedPassword = password; //TODO actually hash and nonce;

        return hashedPassword;
    }


}
