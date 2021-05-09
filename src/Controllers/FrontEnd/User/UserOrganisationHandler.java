package Controllers.FrontEnd.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles user's organisation tab
 */
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


    /**
     * Initialise User Organisation Handler
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Updates orgnisation information
     */
    private void UpdateOrganisationInformation() {

    }
}
