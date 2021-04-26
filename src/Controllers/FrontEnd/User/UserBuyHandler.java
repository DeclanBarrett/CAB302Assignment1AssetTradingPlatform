package Client.EventHandlers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void BuyAsset(ActionEvent BuyAsset) throws IOException {
        System.out.println(BuyAsset.getSource());
    }

    public void AssetTypeChanged(ActionEvent AssetTypeChanged) {

    }

    private void UpdateAssetTypeText() {

    }

    private void UpdateBuyInformation() {

    }
}
