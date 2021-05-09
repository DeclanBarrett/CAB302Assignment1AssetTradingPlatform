package Controllers.FrontEnd.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles User Sell orders
 */
public class UserSellHandler implements Initializable {

    @FXML
    private TextField SellAssetQuantity;
    @FXML
    private TextField SellPriceCredits;
    @FXML
    private Label SellAssetTypeText;
    @FXML
    private Button SellButton;
    @FXML
    private ComboBox SellAssetType;
    @FXML
    private Label SellErrorText;
    @FXML
    private LineChart SellPriceHistoryGraph;
    @FXML
    private TableView SellOrdersTable;

    /**
     * Initialise User Sell Handler
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Handles user sell actions.
     * @param SellAsset
     */
    public void SellAsset(ActionEvent SellAsset) {
        System.out.println(SellAsset.getSource());
    }

    /**
     * Handles change of asset types in sell tab
     * @param AssetTypeChanged
     */
    public void AssetTypeChanged(ActionEvent AssetTypeChanged) {
        System.out.println(AssetTypeChanged.getSource());
    }

    /**
     * Updates asset type text box.
     */
    private void UpdateAssetTypeText() {

    }

    /**
     * Updates sell order information
     */
    private void UpdateSellInformation() {

    }
}
