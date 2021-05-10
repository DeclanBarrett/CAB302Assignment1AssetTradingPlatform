package Controllers.FrontEnd.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles User remove tab
 */
public class UserRemoveHandler implements Initializable {

    @FXML
    Label RemoveOrganisationName;

    @FXML
    TableView RemoveTable;

    Button[] RemoveButtons;

    /**
     * Initialise User Remove Handler
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Remove order from system
     * @param RemoveOrder - order to remove
     */
    public void RemoveOrder(ActionEvent RemoveOrder) {
        System.out.println(RemoveOrder.getSource());
    }

    /**
     * Update remove table
     */
    private void UpdateRemoveTable() {

    }

    /**
     * Update organisational unit text field.
     */
    private void UpdateOrganisationText() {

    }
}
