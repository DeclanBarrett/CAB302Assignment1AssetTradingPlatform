package Controllers.FrontEnd.Admin;

import Controllers.Backend.NetworkObjects.OrganisationalUnit;
import Controllers.FrontEnd.LoginController;
import Controllers.Socket.MockSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    @FXML
    TableColumn<OrganisationalUnit, String> EditOrgNameColumn;
    @FXML
    TableColumn<OrganisationalUnit, String> EditOrgCreditColumn;
    @FXML
    TableColumn<OrganisationalUnit, String> EditOrgAssetNameColumn;
    @FXML
    TableColumn<OrganisationalUnit, String> EditOrgAssetQuantityColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EditOrgNameColumn.setCellValueFactory(new PropertyValueFactory<>("unitName"));
        EditOrgCreditColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        EditOrgAssetNameColumn.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        EditOrgAssetQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("assetQuantity"));
        UpdateOrganisationTable();
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
        List<OrganisationalUnit> orgs = MockSocket.getInstance().GetAllOrganisations(LoginController.GetToken());

        List<OrganisationTableObject> tableOrgs = new ArrayList<>();

        //Convert to organisation table object
        for (OrganisationalUnit org: orgs) {
            org.GetAllAssets().forEach((k, v) ->  tableOrgs.add(new OrganisationTableObject(org.getUnitName(), org.getCredits(), k, v)));
        }

        EditOrgAssetsTable.getItems().setAll(tableOrgs);
    }

    public class OrganisationTableObject {
        String unitName;
        double credits;
        String assetName;
        int assetQuantity;

        public OrganisationTableObject(String unitName, double credits, String assetName, int assetQuantity) {
            this.unitName = unitName;
            this.credits = credits;
            this.assetName = assetName;
            this.assetQuantity = assetQuantity;
        }


        public String getUnitName() {
            return unitName;
        }

        public double getCredits() {
            return credits;
        }

        public String getAssetName() {
            return assetName;
        }

        public int getAssetQuantity() {
            return assetQuantity;
        }
    }

}
