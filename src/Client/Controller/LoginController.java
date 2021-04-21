package Client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    // method allowing the login button to progress user to the user login page.
    public void HandleLoginPress (ActionEvent HandleLoginPress) throws IOException {

        Parent loginView;
        loginView = FXMLLoader.load(getClass().getResource("UserScreen.fxml"));
        Scene loginViewScene = new Scene(loginView);

        Stage window = (Stage)((Node)HandleLoginPress.getSource()).getScene().getWindow();

        window.setScene(loginViewScene);
        window.show();
    }

    // method allowing the login button to progress user to the user login page.
    public void TriggerResetPassword (ActionEvent TriggerResetPassword) throws IOException {

        //Attemptign to help Aidan
        Parent passwordReseter;
        passwordReseter = FXMLLoader.load(getClass().getResource("PasswordReset.fxml"));
        Scene passwordReseterScene = new Scene(passwordReseter);

        Stage window = (Stage)((Node)TriggerResetPassword.getSource()).getScene().getWindow();

        window.setScene(passwordReseterScene);
        window.show();


    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

