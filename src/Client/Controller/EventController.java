package Client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventController implements Initializable {

    // method allowing the login button to progress user to the user login page.
    public void handleLoginPress (ActionEvent loginEvent) throws IOException {
        Parent loginView;
        loginView = FXMLLoader.load(getClass().getResource("UserScreen.fxml"));
        Scene loginViewScene = new Scene(loginView);

        Stage window = (Stage)((Node)loginEvent.getSource()).getScene().getWindow();

        window.setScene(loginViewScene);
        window.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
