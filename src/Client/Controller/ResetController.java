package Client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetController implements Initializable {

    @FXML
    private TextField UsernameTextArea;
    @FXML
    private PasswordField OldPasswordText;
    @FXML
    private PasswordField NewPasswordText;
    @FXML
    private PasswordField NewPassword2Text;

    // method allowing the login button to progress user to the user login page.
    public void ResetPassword (ActionEvent ResetPassword) throws IOException {

        System.out.println(UsernameTextArea.getText());
        System.out.println(OldPasswordText.getText());
        System.out.println(NewPasswordText.getText());
        System.out.println(NewPassword2Text.getText());

        Parent loginView;
        loginView = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene loginViewScene = new Scene(loginView);

        Stage window = (Stage)((Node)ResetPassword.getSource()).getScene().getWindow();

        window.setScene(loginViewScene);
        window.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
