package Controllers.FrontEnd.User;

import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.NetworkObjects.Trade;
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Handles all user buy orders.
 */
public class UserBuyHandler implements Initializable {

    @FXML
    private TextField BuyAssetQuantity;
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
    public void BuyAsset(ActionEvent BuyAsset) throws IOException {
        System.out.println(BuyAsset.getSource());
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
        Date currentDate = new Date();

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
