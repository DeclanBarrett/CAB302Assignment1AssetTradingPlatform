package Controllers.FrontEnd.Admin;

import Controllers.BackEnd.NetworkObjects.OrganisationalUnit;
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
 * Handles events in admin organisation edit tab.
 */
public class AdminCreateOrganisationTabController implements Initializable, Observer {

    public static final String ERROR_TEXT_NOT_NUMBER = "PLEASE ENTER A NUMBER FOR CREDITS";
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
        CreateOrgErrorText.setText("");
        UpdateOrganisationTable();

    }

    /**
     * Creates new organisational unit
     * @param CreateOrganisation Orgnisational unit to be created
     */
    public void CreateOrganisation(ActionEvent CreateOrganisation) {

        String clientResponse = "";

        //Attempt to parse the credit amount
        try {
            Integer credit = Integer.parseInt(CreateOrgCredits.getText());

            UtilFieldCheckers.checkMissingValues(new ArrayList<String>(Arrays.asList(CreateOrgName.getText())));

            //Attempt to add the organisation - it sends an organisation without any assets
            try {
                clientResponse = ClientSocket.getInstance().AddOrganisation(LoginController.GetToken(),
                        new OrganisationalUnit(CreateOrgName.getText(), credit, null));
                CreateOrgErrorText.setTextFill(Color.GREEN);
            } catch (Exception e) {
                CreateOrgErrorText.setTextFill(Color.RED);
            }

        } catch (NumberFormatException e) {
            CreateOrgErrorText.setTextFill(Color.RED);
            clientResponse = ERROR_TEXT_NOT_NUMBER;

        } catch (NullPointerException e) {
            CreateOrgErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        CreateOrgErrorText.setText(clientResponse);
    }

    /**
     * Updates the organisation table with all the organisations on the server
     */
    private void UpdateOrganisationTable() {

        List<OrganisationalUnit> orgs = new ArrayList<>();

        String clientResponse = CreateOrgErrorText.getText();

        try {
            orgs = ClientSocket.getInstance().GetAllOrganisations(LoginController.GetToken());

        } catch (Exception e) {
            clientResponse = e.getMessage();
            CreateOrgErrorText.setTextFill(Color.RED);
        }

        CreateOrgErrorText.setText(clientResponse);
        CreateOrgTable.getItems().setAll(orgs);
    }


    @Override
    public void update(Subject s) {
        UpdateOrganisationTable();
    }
}
