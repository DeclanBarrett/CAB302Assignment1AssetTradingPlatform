package Controllers.FrontEnd.User;

import Controllers.BackEnd.NetworkObjects.Order;
import Controllers.BackEnd.NetworkObjects.OrganisationalUnit;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.BackEnd.Socket.ClientSocket;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;

/**
 * Handles user's organisation tab
 */
public class UserOrganisationTabController implements Initializable, Observer {

    @FXML
    Label OrgName;
    @FXML
    Label OrgTotalCredits;
    @FXML
    Label userOrgErrorText;
    @FXML
    TableView OrgAssetQuantityTable;
    @FXML
    TableColumn<Map, String> OrgAssetTypeNameColumn;
    @FXML
    TableColumn<Map, String> OrgAssetQuantityColumn;

    @FXML
    TableView OrgBuyOrdersTable;
    @FXML
    TableColumn<Order, String> OrgOrderBuyAssetTypeColumn;
    @FXML
    TableColumn<Order, String> OrgOrderBuyQuantityColumn;
    @FXML
    TableColumn<Order, String> OrgOrderBuyPriceColumn;

    @FXML
    TableView OrgSellOrdersTable;
    @FXML
    TableColumn<Order, String> OrgOrderSellAssetTypeColumn;
    @FXML
    TableColumn<Order, String> OrgOrderSellQuantityColumn;
    @FXML
    TableColumn<Order, String> OrgOrderSellPriceColumn;

    OrderManager orderManager;
    /**
     * Initialise User Organisation Handler
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrgAssetTypeNameColumn.setCellValueFactory(new MapValueFactory<>("assetName"));
        OrgAssetQuantityColumn.setCellValueFactory(new MapValueFactory<>("assetQuantity"));

        OrgOrderBuyAssetTypeColumn.setCellValueFactory(new PropertyValueFactory<>("assetType"));
        OrgOrderBuyQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("assetQuantity"));
        OrgOrderBuyPriceColumn.setCellValueFactory(new PropertyValueFactory<>("requestPrice"));

        OrgOrderSellAssetTypeColumn.setCellValueFactory(new PropertyValueFactory<>("assetType"));
        OrgOrderSellQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("assetQuantity"));
        OrgOrderSellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("requestPrice"));

        try {
            orderManager = new OrderManager(ClientSocket.getInstance().GetAllTradeHistory(LoginController.GetToken()));
        } catch (Exception e) {
            userOrgErrorText.setTextFill(Color.RED);
            userOrgErrorText.setText("TRADE HISTORY NOTIFICATIONS DISABLED");
        }

        UpdateOrganisationInformation();
    }

    /**
     * Updates orgnisation information
     */
    private void UpdateOrganisationInformation() {

        String clientResponse = "";

        //Attempt to set the organisation assets and quantities via casting to a map
        try {
            orderManager.checkTrades(ClientSocket.getInstance().GetAllTradeHistory(LoginController.GetToken()));

            OrganisationalUnit org = ClientSocket.getInstance().GetOrganisation(LoginController.GetToken(), LoginController.GetUser().getOrganisationalUnit());//LoginController.GetUser().getOrganisationalUnit()

            OrgName.setText(org.getUnitName());

            OrgTotalCredits.setText(((Double) org.getCredits()).toString());

            ObservableList<Map<String, Object>> items =
                    FXCollections.<Map<String, Object>>observableArrayList();

            //Setting each map
            for (Map.Entry<String, Integer> entry : org.GetAllAssets().entrySet()) {
                String k = entry.getKey();
                Integer v = entry.getValue();
                Map<String, Object> item = new HashMap<>();
                item.put("assetName", k);
                item.put("assetQuantity", v);
                items.add(item);

            }

            OrgAssetQuantityTable.getItems().setAll(items);

            List<Order> buyOrders = ClientSocket.getInstance().GetOrganisationBuyOrders(LoginController.GetToken(), LoginController.GetUser().getOrganisationalUnit());
            OrgBuyOrdersTable.getItems().setAll(buyOrders);

            List<Order> sellOrders = ClientSocket.getInstance().GetOrganisationSellOrders(LoginController.GetToken(), LoginController.GetUser().getOrganisationalUnit());
            OrgSellOrdersTable.getItems().setAll(sellOrders);
            userOrgErrorText.setTextFill(Color.GREEN);
        } catch (Exception e) {
            userOrgErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }
        userOrgErrorText.setText(clientResponse);

    }



    @Override
    public void update(Subject s) {
        UpdateOrganisationInformation();
    }
}
