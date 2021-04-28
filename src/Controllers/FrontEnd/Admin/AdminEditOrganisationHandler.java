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
 * Handles events in admin edit organisational unit tab.
 */
public class AdminEditOrganisationHandler implements Initializable {

    @FXML
    TextField EditOrgName;
    @FXML
    TextField EditOrgSetCreditAmount;
    @FXML
    Button EditOrgSetCreditSubmit;
    @FXML
    TextField EditOrgSetAssetName;
    @FXML
    TextField EditOrgSetAssetQuantity;
    @FXML
    Button EditOrgSubmit;
    @FXML
    Label EditOrgErrorText;
    @FXML
    TableView EditOrgAssetsTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Changes quantity of asset held by organisational unit
     * @param SetAssetQuantityToOrg Asset Quantity set
     */
    public void SetAssetQuantityToOrg(ActionEvent SetAssetQuantityToOrg) {
        System.out.println(SetAssetQuantityToOrg.getSource());
    }

    /**
     * Changes quantity of credits of an organisational unit
     * @param SetCreditOfOrg Sets new credits value
     */
    public void SetCreditOfOrg(ActionEvent SetCreditOfOrg) {
        System.out.println(SetCreditOfOrg.getSource());
    }

    private void UpdateOrganisationTable() {

    }

}
