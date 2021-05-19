package Controllers.FrontEnd.User;

import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.NetworkObjects.Trade;
import Controllers.Backend.OrderType;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.Backend.Socket.MockSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Handles all user buy orders.
 */
public class UserBuyHandler implements Initializable {

    private static final String ERROR_WRONG_INPUT_TYPE = "PLEASE INSERT NUMBERS";
    @FXML
    TextField BuyAssetQuantity;
    @FXML
    TextField BuyPriceCredits;
    @FXML
    Label BuyAssetTypeText;
    @FXML
    Button BuyButton;
    @FXML
    ComboBox<String> BuyAssetType;
    @FXML
    Label BuyErrorText;
    @FXML
    LineChart BuyPriceHistoryGraph;

    @FXML
    TableView BuyOrdersTable;
    @FXML
    TableColumn<Order, String> BuyHandlerOrderAssetTypeColumn;
    @FXML
    TableColumn<Order, String> BuyHandlerOrderQuantityColumn;
    @FXML
    TableColumn<Order, String> BuyHandlerOrderPriceColumn;



    /**
     * Initializes User buy handler
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         BuyHandlerOrderAssetTypeColumn.setCellValueFactory(new PropertyValueFactory<>("assetType"));
         BuyHandlerOrderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("assetQuantity"));
         BuyHandlerOrderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("requestPrice"));

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Price");

        UpdateAssetTypeText();
        UpdateBuyInformation();
    }

    /**
     * Handles buy asset events
     * @param BuyAsset
     * @throws IOException - thrown if in/out exception occurs
     */
    public void BuyAsset(ActionEvent BuyAsset) {
        if (BuyAssetType.getValue() == null) {
            BuyErrorText.setText("NO ASSET TYPE SELECTED");
            return;
        }
        try {
            Integer quantity = 0;
            Float price = 0.0f;
            try {
                quantity = Integer.parseInt(BuyAssetQuantity.getText());
                price = Float.parseFloat(BuyPriceCredits.getText());
            } catch (NumberFormatException formatException) {
                BuyErrorText.setText(ERROR_WRONG_INPUT_TYPE);
                return;
            }

            MockSocket.getInstance().AddOrder(LoginController.GetToken(),
                    new Order(-1, OrderType.BUY, BuyAssetType.getValue(), quantity, price, LoginController.GetUser().getOrganisationalUnit(), null));
            BuyErrorText.setText("ORDER WAS SUCCESSFULLY PLACED");
        } catch (Exception e) {
            BuyErrorText.setText("ERROR");
        }
        UpdateBuyInformation();


    }

    /**
     * Updates change of asset type.
     * @param AssetTypeChanged
     */
    public void AssetTypeChanged(ActionEvent AssetTypeChanged) {
        UpdateBuyInformation();
    }

    /**
     * Updates asset type textbox
     */
    private void UpdateAssetTypeText() {
        BuyAssetType.getItems().setAll(MockSocket.getInstance().GetAssetTypes(LoginController.GetToken()));
    }

    /**
     * Updates buy information
     */
    private void UpdateBuyInformation() {
        List<Order> sellOrders = MockSocket.getInstance().GetSellOrders(LoginController.GetToken());
        BuyOrdersTable.getItems().setAll(sellOrders);

        List<Trade> trades = MockSocket.getInstance().GetTradeHistory(LoginController.GetToken(), BuyAssetType.getValue());
        XYChart.Series tradeData = new XYChart.Series();

        tradeData.setName("Price of " + BuyAssetType.getValue());

        for (Trade trade: trades) {
            tradeData.getData().add(new XYChart.Data(trade.getTradeDateMilSecs().getTime(), trade.getAssetPrice()));
        }

        BuyPriceHistoryGraph.getData().setAll(tradeData);

    }
}
