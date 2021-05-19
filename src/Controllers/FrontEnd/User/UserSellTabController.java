package Controllers.FrontEnd.User;

import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.NetworkObjects.Trade;
import Controllers.Backend.OrderType;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.Backend.Socket.MockSocket;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

/**
 * Handles User Sell orders
 */
public class UserSellTabController implements Initializable, Observer {

    private static final String ERROR_WRONG_INPUT_TYPE = "PLEASE INSERT NUMBERS";
    @FXML
    private TextField SellAssetQuantity;
    @FXML
    private TextField SellPriceCredits;
    @FXML
    private Label SellAssetTypeText;
    @FXML
    private Button SellButton;
    @FXML
    private ComboBox<String> SellAssetType;
    @FXML
    private Label SellErrorText;
    @FXML
    private LineChart SellPriceHistoryGraph;


    @FXML
    private TableView SellOrdersTable;
    @FXML
    TableColumn<Order, String> SellHandlerOrderAssetTypeColumn;
    @FXML
    TableColumn<Order, String> SellHandlerOrderQuantityColumn;
    @FXML
    TableColumn<Order, String> SellHandlerOrderPriceColumn;


    /**
     * Initialise User Sell Handler
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SellHandlerOrderAssetTypeColumn.setCellValueFactory(new PropertyValueFactory<>("assetType"));
        SellHandlerOrderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("assetQuantity"));
        SellHandlerOrderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("requestPrice"));

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Price");

        UpdateAssetTypeText();
        UpdateSellInformation();
    }

    /**
     * Handles user sell actions.
     * @param SellAsset
     */
    public void SellAsset(ActionEvent SellAsset) {
        if (SellAssetType.getValue() == null) {
            SellErrorText.setText("NO ASSET TYPE SELECTED");
            return;
        }
        try {
            Integer quantity = 0;
            Float price = 0.0f;
            try {
                quantity = Integer.parseInt(SellAssetQuantity.getText());
                price = Float.parseFloat(SellPriceCredits.getText());
            } catch (NumberFormatException formatException) {
                SellErrorText.setText(ERROR_WRONG_INPUT_TYPE);
                return;
            }

            MockSocket.getInstance().AddOrder(LoginController.GetToken(),
                    new Order(-1, OrderType.SELL, SellAssetType.getValue(), quantity, price, LoginController.GetUser().getOrganisationalUnit(), null));

            SellErrorText.setText("ORDER WAS SUCCESSFULLY PLACED");
        } catch (Exception e) {
            SellErrorText.setText("ERROR");
        }
        UpdateSellInformation();
        System.out.println(SellAsset.getSource());
    }

    /**
     * Handles change of asset types in sell tab
     * @param AssetTypeChanged
     */
    public void AssetTypeChanged(ActionEvent AssetTypeChanged) {
        UpdateSellInformation();
    }

    /**
     * Updates asset type text box.
     */
    private void UpdateAssetTypeText() {
        try {
            SellAssetType.getItems().setAll(MockSocket.getInstance().GetAssetTypes(LoginController.GetToken()));
        } catch (Exception e) {

        }

    }

    /**
     * Updates sell order information
     */
    private void UpdateSellInformation() {
        Date currentDate = new Date();

        List<Order> sellOrders = MockSocket.getInstance().GetBuyOrders(LoginController.GetToken());
        SellOrdersTable.getItems().setAll(sellOrders);

        List<Trade> trades = MockSocket.getInstance().GetTradeHistory(LoginController.GetToken(), SellAssetType.getValue());
        XYChart.Series tradeData = new XYChart.Series();

        tradeData.setName("Price of " + SellAssetType.getValue());

        for (Trade trade: trades) {
            tradeData.getData().add(new XYChart.Data(trade.getTradeDateMilSecs().getTime(), trade.getAssetPrice()));
        }

        SellPriceHistoryGraph.getData().setAll(tradeData);
    }

    @Override
    public void update(Subject s) {

    }
}
