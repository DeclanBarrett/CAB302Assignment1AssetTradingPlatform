package Controllers.FrontEnd.User;

import Controllers.BackEnd.NetworkObjects.Order;
import Controllers.BackEnd.NetworkObjects.Trade;
import Controllers.BackEnd.OrderType;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.BackEnd.Socket.ClientSocket;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import Controllers.Utils.UtilFieldCheckers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;

/**
 * Handles User Sell orders
 */
public class UserSellTabController implements Initializable, Observer {

    private static final String ERROR_WRONG_INPUT_TYPE = "PLEASE INSERT NUMBERS";
    @FXML TextField SellAssetQuantity;
    @FXML TextField SellPriceCredits;
    @FXML Label SellAssetTypeText;
    @FXML Button SellButton;
    @FXML ComboBox<String> SellAssetType;
    @FXML Label SellErrorText;
    @FXML LineChart SellPriceHistoryGraph;


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

        SellErrorText.setText("");
        UpdateAssetTypeText();
        UpdateSellInformation();
    }

    /**
     * Handles user sell actions.
     * @param SellAsset
     */
    public void SellAsset(ActionEvent SellAsset) {

        String clientResponse = "";

        try {
            //Send an order to the database
            UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList(SellAssetType.getValue())));
            Integer quantity = Integer.parseInt(SellAssetQuantity.getText());
            Float price = Float.parseFloat(SellPriceCredits.getText());

            clientResponse = ClientSocket.getInstance().AddOrder(LoginController.GetToken(),
                    new Order(-1, OrderType.SELL, SellAssetType.getValue(), quantity, price, LoginController.GetUser().getOrganisationalUnit(), null));
            SellErrorText.setTextFill(Color.GREEN);
        } catch (NumberFormatException formatException) {
            SellErrorText.setTextFill(Color.RED);
            clientResponse = ERROR_WRONG_INPUT_TYPE;
        } catch (Exception e) {
            SellErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        UpdateSellInformation();
        SellErrorText.setText(clientResponse);
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
            SellAssetType.getItems().setAll(ClientSocket.getInstance().GetAssetTypes(LoginController.GetToken()));
        } catch (Exception e) {
            SellErrorText.setText(e.getMessage());
            SellErrorText.setTextFill(Color.RED);
        }

    }

    /**
     * Updates sell order information
     */
    private void UpdateSellInformation() {

        List<Order> sellOrders = new ArrayList<>();
        XYChart.Series tradeData = new XYChart.Series();

        String clientResponse = SellErrorText.getText();
        try {
            sellOrders = ClientSocket.getInstance().GetBuyOrders(LoginController.GetToken());

            List<Trade> trades = ClientSocket.getInstance().GetTradeHistory(LoginController.GetToken(), SellAssetType.getValue());

            tradeData.setName("Price of " + SellAssetType.getValue());

            for (Trade trade: trades) {
                tradeData.getData().add(new XYChart.Data(trade.getTradeDateMilSecs().getTime(), trade.getAssetPrice()));
            }
        } catch (Exception e) {
            SellErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        SellErrorText.setText(clientResponse);
        SellOrdersTable.getItems().setAll(sellOrders);
        SellPriceHistoryGraph.getData().setAll(tradeData);
    }

    @Override
    public void update(Subject s) {
        UpdateSellInformation();
    }
}
