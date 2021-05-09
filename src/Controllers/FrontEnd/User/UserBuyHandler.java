package Controllers.FrontEnd.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles all user buy orders.
 */
public class UserBuyHandler implements Initializable {

    @FXML
    private TextField BuyAssetQuantity;
    @FXML
    private TextField BuyPriceCredits;
    @FXML
    private Label BuyAssetTypeText;
    @FXML
    private Button BuyButton;
    @FXML
    private ComboBox BuyAssetType;
    @FXML
    private Label BuyErrorText;
    @FXML
    private LineChart BuyPriceHistoryGraph;
    @FXML
    private TableView BuyOrdersTable;

    /**
     * Initializes User buy handler
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Handles buy asset events
     * @param BuyAsset
     * @throws IOException - thrown if in/out exception occurs
     */
    public void BuyAsset(ActionEvent BuyAsset) throws IOException {
        System.out.println(BuyAsset.getSource());
    }

    /**
     * Updates change of asset type.
     * @param AssetTypeChanged
     */
    public void AssetTypeChanged(ActionEvent AssetTypeChanged) {

    }

    /**
     * Updates asset type textbox
     */
    private void UpdateAssetTypeText() {

    }

    /**
     * Updates buy information
     */
    private void UpdateBuyInformation() {

    }
}
