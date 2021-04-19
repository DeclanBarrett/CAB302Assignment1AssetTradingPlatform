package Client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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

    @FXML
    private Label ErrorText;

    private boolean passwordsMatch;

    // method allowing the login button to progress user to the user login page.
    public void ResetPassword (ActionEvent ResetPassword) throws IOException {

        System.out.println(UsernameTextArea.getText());
        System.out.println(OldPasswordText.getText());
        System.out.println(NewPasswordText.getText());
        System.out.println(NewPassword2Text.getText());
        System.out.println(passwordsMatch);

        if (passwordsMatch) {
            if (CheckExistingPassword(UsernameTextArea.getText(), OldPasswordText.getText())) {
                UpdatePassword();

                Parent loginView;
                loginView = FXMLLoader.load(getClass().getResource("login.fxml"));
                Scene loginViewScene = new Scene(loginView);

                Stage window = (Stage)((Node)ResetPassword.getSource()).getScene().getWindow();

                window.setScene(loginViewScene);
                window.show();
            } else {
                ErrorText.setText("USERNAME OR PASSWORD INCORRECT");
            }
        }




    }

    public void CheckPasswordDifference(KeyEvent CheckPasswordDifference) throws IOException {
        if (!NewPasswordText.getText().equals(NewPassword2Text.getText())) {
            ErrorText.setText("PASSWORDS DO NOT MATCH");
            passwordsMatch = false;
            System.out.println(passwordsMatch);
        } else {
            ErrorText.setText("ALL GOOD");
            passwordsMatch = true;
            System.out.println(passwordsMatch);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private boolean CheckExistingPassword(String username, String password) {
        /**TODO GO TO THE DATBASE AND CHECK
         *
         */

        return username.equals("ANDY") && password.equals("123");
    }

    private void UpdatePassword() {
        /**TODO GO TO THE DATBASE AND UPDATE
         *
         */
    }


}
