package Controllers.FrontEnd.User;

import Controllers.Backend.NetworkObjects.User;
import Controllers.Backend.NetworkObjects.UserInfo;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import Controllers.Utils.UtilLoginSecurity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Handles User tab
 */
public class UserUserTabController implements Initializable, Observer {

    @FXML
    Label UserName;
    @FXML
    Label UserType;
    @FXML
    Button UserLogoutButton;
    @FXML
    PasswordField UserOldPassword;
    @FXML
    PasswordField UserNewPassword;
    @FXML
    PasswordField UserNewPassword2;
    @FXML
    Button UserResetButton;

    /**
     * Initialises User Handler
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateUserInformation();
    }

    /**
     * Logs user out when requested
     * @param LogOutUser
     */
    public void LogOutUser(ActionEvent LogOutUser) {

        LoginController.Logout();
    }

    /**
     * Handles reset password events.
     * @param ResetPassword
     */
    public void ResetPassword (ActionEvent ResetPassword) {
        System.out.println(ResetPassword.getSource());
    }

    private void UpdateUserInformation() {
        UserInfo user = LoginController.GetUser();

        try {
            UserName.setText(user.getUsername());
            UserType.setText(user.getAccountType().toString());
        } catch (Exception e) {
            UserName.setText("NOT LOGGED IN");
        }
    }

    @Override
    public void update(Subject s) {
        //UpdateUserInformation();
    }
}
