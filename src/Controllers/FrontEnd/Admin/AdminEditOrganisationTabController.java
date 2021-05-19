package Controllers.FrontEnd.Admin;

import Controllers.Backend.NetworkObjects.OrganisationalUnit;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.Backend.Socket.MockSocket;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
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
public class AdminEditOrganisationTabController implements Initializable, Observer {

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

        if (EditOrgName.getText() == null || EditOrgName.getText().equals("")) {
            EditOrgErrorText.setText("NO ORGANISATION");
            return;
        }


        Integer assetQuantity = 0;
        try {
            assetQuantity = Integer.parseInt(EditOrgSetAssetQuantity.getText());

        } catch (Exception e) {
            EditOrgErrorText.setText("PLEASE ENTER AN INTEGER FOR QUANTITY");
        }

        String success = MockSocket.getInstance().UpdateOrganisationAsset(LoginController.GetToken(), EditOrgName.getText(), EditOrgSetAssetName.getText(), assetQuantity);
        EditOrgErrorText.setText(success);

    }

    /**
     * Changes quantity of credits of an organisational unit
     * @param SetCreditOfOrg Sets new credits value
     */
    public void SetCreditOfOrg(ActionEvent SetCreditOfOrg) {

        if (EditOrgName.getText() == null || EditOrgName.getText().equals("")) {
            EditOrgErrorText.setText("NO ORGANISATION");
            return;
        }

        Integer assetQuantity = 0;
        try {
            assetQuantity = Integer.parseInt(EditOrgSetAssetQuantity.getText());

        } catch (Exception e) {
            EditOrgErrorText.setText("PLEASE ENTER AN INTEGER FOR QUANTITY");
        }

        String success = MockSocket.getInstance().UpdateOrganisationAsset(LoginController.GetToken(), EditOrgName.getText(), EditOrgSetAssetName.getText(), assetQuantity);
        EditOrgErrorText.setText(success);
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

    @Override
    public void update(Subject s) {
        UpdateOrganisationTable();
    }

    /**
     * Object constructor for Organisation Table Objects.
     */
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
