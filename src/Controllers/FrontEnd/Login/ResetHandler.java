package Controllers.FrontEnd.Login;

import Controllers.BackEnd.Socket.MockSocket;
import Controllers.Exceptions.LoginException;
import Controllers.Utils.UtilFieldCheckers;
import Controllers.Utils.UtilLoginSecurity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles events on the reset password page.
 */
public class ResetHandler implements Initializable {

    @FXML
    private TextField UsernameTextArea;
    @FXML
    private PasswordField OldPasswordText;
    @FXML
    private PasswordField NewPasswordText;
    @FXML
    private PasswordField NewPassword2Text;

    @FXML
    private Label ErrorText;

    boolean isPasswordMatching;

    private LoginController loginController;

    private static final String ERROR_TEXT_RESET_PASSWORD = "USERNAME OR PASSWORD INCORRECT";

    /**
     * Checks that the correct username/password combination is entered into the reset password screen
     * this then returns the user to the login screen after password reset.
     * @param ResetPassword Perform action on ResetPassword button press
     * @throws IOException Handles error in file entry/output
     */
    public void ResetPassword (ActionEvent ResetPassword) {
        if (isPasswordMatching) {
            try {
                if (CheckExistingPassword(UsernameTextArea.getText(), OldPasswordText.getText())) {
                    //Hashes the password
                    UtilLoginSecurity loginSecurity = new UtilLoginSecurity();
                    String hashPassword = loginSecurity.generateHashedPassword(UsernameTextArea.getText(), NewPasswordText.getText());
                    //Attempts the password reset
                    ErrorText.setText(MockSocket.getInstance().AttemptResetPassword(LoginController.GetToken(), UsernameTextArea.getText(), hashPassword));
                    loginController.Logout();
                } else {
                    throw new LoginException(ERROR_TEXT_RESET_PASSWORD);
                }
            } catch (Exception e) {
                ErrorText.setTextFill(Color.RED);
                ErrorText.setText(ERROR_TEXT_RESET_PASSWORD);
            }
        }

    }


    /**
     * Checks that the two 'new' passwords entered are the same.
     * @throws IOException handles error in file entry/output
     */
    public void CheckPasswordDifference (KeyEvent CheckPasswordDifference) {

        String clientResponse = "";
        try {
            clientResponse = UtilFieldCheckers.checkTwoStrings(NewPasswordText.getText(), NewPassword2Text.getText(), "PASSWORDS");
            isPasswordMatching = true;
            ErrorText.setTextFill(Color.GREEN);
        } catch (IllegalArgumentException e) {
            isPasswordMatching = false;
            clientResponse = e.getMessage();
            ErrorText.setTextFill(Color.RED);
        }

        ErrorText.setText(clientResponse);

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginController = new LoginController();
    }

}
