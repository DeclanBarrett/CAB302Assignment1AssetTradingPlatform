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

public class ResetController implements Initializable {

    // method allowing the login button to progress user to the user login page.
    public void ResetPassword (ActionEvent ResetPassword) throws IOException {

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
