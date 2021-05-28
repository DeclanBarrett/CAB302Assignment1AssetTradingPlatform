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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Handles all user buy orders.
 */
public class UserBuyTabController implements Initializable, Observer {

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


        BuyErrorText.setText("");
        UpdateAssetTypeText();
        UpdateBuyInformation();
    }

    /**
     * Handles buy asset events
     * @param BuyAsset
     * @throws IOException - thrown if in/out exception occurs
     */
    public void BuyAsset(ActionEvent BuyAsset) {

        String clientResponse = "";

        try {
            //Send an order to the database
            UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList(BuyAssetType.getValue())));

            Integer quantity = Integer.parseInt(BuyAssetQuantity.getText());
            Float price = Float.parseFloat(BuyPriceCredits.getText());

            ClientSocket.getInstance().AddOrder(LoginController.GetToken(),
                    new Order(-1, OrderType.BUY, BuyAssetType.getValue(), quantity, price, LoginController.GetUser().getOrganisationalUnit(), null));
            clientResponse = "ORDER WAS SUCCESSFULLY PLACED";
            System.out.println("IN: " +  clientResponse);
            BuyErrorText.setTextFill(Color.GREEN);
        } catch (NumberFormatException formatException) {
            clientResponse = ERROR_WRONG_INPUT_TYPE;
            BuyErrorText.setTextFill(Color.RED);
            System.out.println("FORMAT: " +  clientResponse);
        } catch (Exception e) {
            clientResponse = e.getMessage();
            BuyErrorText.setTextFill(Color.RED);
            System.out.println("EXCE: " +  clientResponse);
        }

        System.out.println("IS THIS TRIGGERING: " + clientResponse);
        BuyErrorText.setText(clientResponse);
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
        try {
            BuyAssetType.getItems().setAll(ClientSocket.getInstance().GetAssetTypes(LoginController.GetToken()));
        } catch (Exception e) {
            BuyErrorText.setText(e.getMessage());
            BuyErrorText.setTextFill(Color.RED);
        }
    }

    /**
     * Updates buy information
     */
    private void UpdateBuyInformation() {

        List<Order> sellOrders = new ArrayList<>();
        XYChart.Series tradeData = new XYChart.Series();

        String clientResponse = BuyErrorText.getText();

        try {
            //Update the buy orders table
            sellOrders = ClientSocket.getInstance().GetSellOrders(LoginController.GetToken());


            //Update the graph
            List<Trade> trades = ClientSocket.getInstance().GetTradeHistory(LoginController.GetToken(), BuyAssetType.getValue());

            tradeData.setName("Price of " + BuyAssetType.getValue());

            for (Trade trade: trades) {
                tradeData.getData().add(new XYChart.Data(trade.getTradeDateMilSecs().getTime(), trade.getAssetPrice()));
            }

            //BuyErrorText.setTextFill(Color.GREEN);
        } catch (Exception e) {
            clientResponse = e.getMessage();
            BuyErrorText.setTextFill(Color.RED);
        }

        BuyPriceHistoryGraph.getData().setAll(tradeData);
        BuyOrdersTable.getItems().setAll(sellOrders);
        BuyErrorText.setText(clientResponse);

    }

    @Override
    public void update(Subject s) {
        UpdateBuyInformation();
    }
}
