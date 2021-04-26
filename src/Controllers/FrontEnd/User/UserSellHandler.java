package Client.EventHandlers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void SellAsset(ActionEvent SellAsset) {
        System.out.println(SellAsset.getSource());
    }

    public void AssetTypeChanged(ActionEvent AssetTypeChanged) {
        System.out.println(AssetTypeChanged.getSource());
    }

    private void UpdateAssetTypeText() {

    }

    private void UpdateSellInformation() {

    }
}
