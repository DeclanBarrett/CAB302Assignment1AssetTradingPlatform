package Controllers.FrontEnd.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class UserUserHandler implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void LogOutUser(ActionEvent LogOutUser) {
        System.out.println(LogOutUser.getSource());
    }

    public void ResetPassword (ActionEvent ResetPassword) {
        System.out.println(ResetPassword.getSource());
    }
}
