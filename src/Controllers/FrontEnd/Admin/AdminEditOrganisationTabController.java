package Controllers.FrontEnd.Admin;

import Controllers.BackEnd.NetworkObjects.OrganisationalUnit;
import Controllers.BackEnd.Socket.MockSocket;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.BackEnd.Socket.ClientSocket;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import Controllers.Utils.UtilFieldCheckers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

        String clientResponse = "";

        Integer assetQuantity = 0;
        try {
            UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList(EditOrgName.getText(), EditOrgSetAssetName.getText())));

            //Attempt to parse and then update the organisation values
            assetQuantity = Integer.parseInt(EditOrgSetAssetQuantity.getText());

            clientResponse = ClientSocket.getInstance().UpdateOrganisationAsset(LoginController.GetToken(),
                    EditOrgName.getText(),
                    EditOrgSetAssetName.getText(),
                    assetQuantity);

            EditOrgErrorText.setTextFill(Color.GREEN);

        } catch (Exception e) {
            EditOrgErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        EditOrgErrorText.setText(clientResponse);

    }

    /**
     * Changes quantity of credits of an organisational unit
     * @param SetCreditOfOrg Sets new credits value
     */
    public void SetCreditOfOrg(ActionEvent SetCreditOfOrg) {

        String clientResponse = "";

        Integer assetQuantity = 0;
        try {
            UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList(EditOrgName.getText())));

            //Attempt to parse and then update the organisation values
            assetQuantity = Integer.parseInt(EditOrgSetAssetQuantity.getText());

            clientResponse = ClientSocket.getInstance().UpdateOrganisationCredit(LoginController.GetToken(),
                    EditOrgName.getText(),
                    assetQuantity);

            EditOrgErrorText.setTextFill(Color.GREEN);

        } catch (Exception e) {
            EditOrgErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        EditOrgErrorText.setText(clientResponse);

    }

    /**
     * Updates the organisation table
     */
    private void UpdateOrganisationTable() {

        List<OrganisationalUnit> orgs = new ArrayList<>();

        List<OrganisationTableObject> tableOrgs = new ArrayList<>();

        try {
            orgs = ClientSocket.getInstance().GetAllOrganisations(LoginController.GetToken());

            //Convert to organisation table object
            for (OrganisationalUnit org: orgs) {
                org.GetAllAssets().forEach((k, v) ->  tableOrgs.add(new OrganisationTableObject(org.getUnitName(), org.getCredits(), k, v)));
            }

        } catch (Exception e) {
            EditOrgErrorText.setText(e.getMessage());
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
