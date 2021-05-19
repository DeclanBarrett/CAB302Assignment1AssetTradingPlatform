package Controllers.FrontEnd.Login;

import Controllers.Backend.Socket.MockSocket;
import Controllers.Exceptions.LoginException;
import Controllers.Utils.UtilSceneChanger;
import Controllers.Utils.UtilFieldCheckers;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for password resets
 */
public class ResetController {

    public static final String ERROR_TEXT_RESET_PASSWORD = "USERNAME OR PASSWORD INCORRECT";
    public static final String LOGIN_PAGE_FILE_PATH = "/Views/Login/Login.fxml";
    LoginController loginController;

    private boolean isPasswordMatching;

    public ResetController() {
        this.loginController = new LoginController();
    }

    /**
     * Resets the password
     * @param username - username of user to change password
     * @param oldPassword - old password to login with
     * @param newPassword - new password to set it to
     * @param window - to be able to set it to a login page
     * @param error - text to set
     */
    public void resetPassword(String username, String oldPassword, String newPassword, Stage window, Label error) {
        if (isPasswordMatching) {
            try {
                if (CheckExistingPassword(username, oldPassword)) {
                    MockSocket.getInstance().AttemptResetPassword(LoginController.GetToken(), username, newPassword);
                    loginController.Logout();

                    UtilSceneChanger.getInstance().ChangeToScene(UtilSceneChanger.SceneType.LOGIN);
                } else {
                    throw new LoginException(ERROR_TEXT_RESET_PASSWORD);
                }
            } catch (Exception e) {
                error.setText(ERROR_TEXT_RESET_PASSWORD);
            }
        }
    }

    /**
     * Returns a boolean 'true' if the correct password combination is entered.
     * @param username Username of current user
     * @param password Password of current user
     * @return
     */
    private boolean CheckExistingPassword(String username, String password) {
        try {
            loginController.AttemptLogin(username, password);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Checks that the two 'new' passwords entered are the same.
     * @throws IOException handles error in file entry/output
     */
    public void CheckPasswordDifference(String newPasswordText, String newPassword2Text, Label errorText) {
        UtilFieldCheckers checker = new UtilFieldCheckers();
        isPasswordMatching = checker.CheckTwoStrings(newPasswordText, newPassword2Text, errorText, "PASSWORDS");

    }

}
