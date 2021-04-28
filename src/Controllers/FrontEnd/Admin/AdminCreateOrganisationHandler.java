package Controllers.FrontEnd.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles events in admin organisation edit tab.
 */
public class AdminCreateOrganisationHandler implements Initializable {

    @FXML
    TextField CreateOrgName;
    @FXML
    TextField CreateOrgCredits;
    @FXML
    Label CreateOrgErrorText;
    @FXML
    Button CreateOrgSubmit;
    @FXML
    TableView CreateOrgTable;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Creates new organisational unit
     * @param CreateOrganisation Orgnisational unit to be created
     */
    public void CreateOrganisation(ActionEvent CreateOrganisation) {
        System.out.println(CreateOrganisation.getSource());
    }

    private void UpdateOrganisationTable() {

    }

}
