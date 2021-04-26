package Client.EventHandlers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserRemoveHandler implements Initializable {

    @FXML
    Label RemoveOrganisationName;

    @FXML
    TableView RemoveTable;

    Button[] RemoveButtons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void RemoveOrder(ActionEvent RemoveOrder) {
        System.out.println(RemoveOrder.getSource());
    }

    private void UpdateRemoveTable() {

    }

    private void UpdateOrganisationText() {

    }
}
