package Controllers.FrontEnd.Login;

import Controllers.Utils.UtilFieldCheckers;
import com.mysql.cj.x.protobuf.MysqlxSession;
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

/**
 * Handles events on the reset password page
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

    ResetController resetController;




    /**
     * Checks that the correct username/password combination is entered into the reset password screen
     * this then returns the user to the login screen after password reset.
     * @param ResetPassword Perform action on ResetPassword button press
     * @throws IOException Handles error in file entry/output
     */
    public void ResetPassword (ActionEvent ResetPassword) {

        System.out.println(UsernameTextArea.getText());
        System.out.println(OldPasswordText.getText());
        System.out.println(NewPasswordText.getText());
        System.out.println(NewPassword2Text.getText());

        resetController.resetPassword(UsernameTextArea.getText(),
                OldPasswordText.getText(),
                NewPasswordText.getText(),
                (Stage)((Node) ResetPassword.getSource()).getScene().getWindow(),
                ErrorText
        );

    }

    public void CheckPasswordDifference (ActionEvent CheckPasswordDifference) {
        resetController.CheckPasswordDifference(NewPasswordText.getText(), NewPassword2Text.getText(), ErrorText);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetController = new ResetController();
    }

}
