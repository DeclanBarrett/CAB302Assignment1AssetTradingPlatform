package Controllers.FrontEnd.Login;


import Controllers.Backend.NetworkObjects.LoginToken;
import Controllers.Backend.NetworkObjects.UserInfo;
import Controllers.Backend.Socket.MockSocket;
import Controllers.Exceptions.LoginException;
import Controllers.Utils.UtilLoginSecurity;
import Controllers.Utils.UtilSceneChanger;

/**
 * Used to check for correct login input.
 */
public class LoginController {

    public static final String LOGIN_ERROR_USERNAME_PASSWORD_1 = "USERNAME OR PASSWORD INCORRECT";
    private static LoginToken currentLogin;
    private static UserInfo currentUser;

    public static UserInfo GetUser() {
        return currentUser;
    }

    public static LoginToken GetToken() {
        return currentLogin;
    }

    /**
     * Logs the user out locally
     */
    public static void Logout() {
        currentLogin = null;
        currentUser = null;
        UtilSceneChanger.getInstance().ChangeToScene(UtilSceneChanger.SceneType.LOGIN);
    }

    /**
     * Global Utility for attempting to login to the server
     */
    public void AttemptLogin(String username, String password) throws LoginException {

        //Get the hashed password
        UtilLoginSecurity loginSecurity = new UtilLoginSecurity();
        String hashPassword = loginSecurity.generateHashedPassword(username, password);

        currentLogin = MockSocket.getInstance().AttemptLogin(username, hashPassword);

        if (isCurrentLogin()) {
            currentUser = MockSocket.getInstance().GetUser(GetToken(), username);
            return;
        }

        throw new LoginException(LOGIN_ERROR_USERNAME_PASSWORD_1);
    }

    private boolean isCurrentLogin() {
        return currentLogin != null;
    }




}
