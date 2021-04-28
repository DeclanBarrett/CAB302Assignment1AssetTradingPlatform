package Controllers.FrontEnd;

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

    /**
     * Checks that the correct username/password combination is entered into the reset password screen
     * this then returns the user to the login screen after password reset.
     * @param ResetPassword Perform action on ResetPassword button press
     * @throws IOException Handles error in file entry/output
     */
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
                loginView = FXMLLoader.load(getClass().getResource("/Views/Login/Login.fxml"));
                Scene loginViewScene = new Scene(loginView);

                Stage window = (Stage)((Node)ResetPassword.getSource()).getScene().getWindow();

                window.setScene(loginViewScene);
                window.show();
            } else {
                ErrorText.setText("USERNAME OR PASSWORD INCORRECT");
            }
        }
    }

    /**
     * Checks that the two 'new' passwords entered are the same.
     * @param CheckPasswordDifference
     * @throws IOException handles error in file entry/output
     */
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

    /**
     * Returns a boolean 'true' if the correct password combination is entered.
     * @param username Username of current user
     * @param password Password of current user
     * @return
     */
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
