package Controllers.FrontEnd;


import Controllers.Backend.NetworkObjects.LoginToken;
import Controllers.Backend.NetworkObjects.UserInfo;
import Controllers.Socket.MockSocket;

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

        currentLogin = MockSocket.Login(username, hashPassword);



        if (currentLogin != null) {
            currentUserInfo = new UserInfo(MockSocket.GetUser(username).GetUsername(), MockSocket.GetUser(username).GetAccountType(), MockSocket.GetUser(username).GetOrganisationalUnit());
            return;
        }

        throw new LoginException("Username or Password Incorrect 1");
    }




    private String ReceiveNonceAndHash(String username, String password) throws LoginException {

        String salt = MockSocket.RetrieveNonce(username);



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
