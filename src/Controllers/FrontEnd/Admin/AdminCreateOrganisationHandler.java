package Controllers.FrontEnd.Admin;

import Controllers.Backend.NetworkObjects.OrganisationalUnit;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.Backend.Socket.MockSocket;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
