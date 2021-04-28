package Controllers.FrontEnd.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserOrganisationHandler implements Initializable {

    @FXML
    private Label OrgName;
    @FXML
    private Label OrgTotalCredits;
    @FXML
    private TableView OrgAssetQuantityTable;
    @FXML
    private TableView OrgBuyOrdersTable;
    @FXML
    private TableView OrgSellOrdersTable;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void UpdateOrganisationInformation() {

    }
}
