package Controllers.FrontEnd.Admin;

import Controllers.Backend.NetworkObjects.OrganisationalUnit;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.Backend.Socket.MockSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
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

    @FXML
    TableColumn<OrganisationalUnit, String> CreateOrgNameColumn;
    @FXML
    TableColumn<OrganisationalUnit, String> CreateOrgCreditColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CreateOrgNameColumn.setCellValueFactory(new PropertyValueFactory<>("unitName"));
        CreateOrgCreditColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        UpdateOrganisationTable();
    }

    /**
     * Creates new organisational unit
     * @param CreateOrganisation Orgnisational unit to be created
     */
    public void CreateOrganisation(ActionEvent CreateOrganisation) {
        System.out.println(CreateOrganisation.getSource());
    }

    private void UpdateOrganisationTable() {
        List<OrganisationalUnit> orgs = MockSocket.getInstance().GetAllOrganisations(LoginController.GetToken());
        CreateOrgTable.getItems().setAll(orgs);
    }

}
