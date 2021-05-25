package Controllers.FrontEnd.User;

import Controllers.BackEnd.NetworkObjects.UserInfo;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import Controllers.Utils.UtilSceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
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
    Button UserResetButton;
    @FXML
    Button userExitButton;
    @FXML
    Label userUserErrorText;

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
        UtilSceneChanger.getInstance().ChangeToScene(UtilSceneChanger.SceneType.RESET);
    }

    public void ExitApplication(ActionEvent ExitApplication) {
        UtilSceneChanger.getInstance().CloseApplication();
    }

    private void UpdateUserInformation() {

        try {
            UserInfo user = LoginController.GetUser();
            UserName.setText(user.getUsername());
            UserType.setText("ACCOUNT TYPE: " + user.getAccountType().toString());
        } catch (Exception e) {
            userUserErrorText.setTextFill(Color.RED);
            UserName.setText("PLEASE LOG IN");
            UserType.setText("PLEASE LOG IN");
            userUserErrorText.setText("PLEASE LOG IN");
        }
    }

    @Override
    public void update(Subject s) {
        UpdateUserInformation();
    }
}
