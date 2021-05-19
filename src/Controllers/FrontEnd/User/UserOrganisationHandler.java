package Controllers.FrontEnd.User;

import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.NetworkObjects.OrganisationalUnit;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.Backend.Socket.MockSocket;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Handles user's organisation tab
 */
public class UserOrganisationHandler implements Initializable {

    @FXML
    Label OrgName;
    @FXML
    Label OrgTotalCredits;
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

        UpdateOrganisationInformation();

        updatePage();
    }

    /**
     * Updates orgnisation information
     */
    private void UpdateOrganisationInformation() {

        //Attempt to set the organisation assets and quantities via casting to a map
        OrganisationalUnit org = MockSocket.getInstance().GetOrganisation(LoginController.GetToken(), "Sales");//LoginController.GetUser().getOrganisationalUnit()

        OrgName.setText(org.getUnitName());

        OrgTotalCredits.setText(((Double) org.getCredits()).toString());

        ObservableList<Map<String, Object>> items =
                FXCollections.<Map<String, Object>>observableArrayList();

        //Setting each map to have an as
        for (Map.Entry<String, Integer> entry : org.GetAllAssets().entrySet()) {
            String k = entry.getKey();
            Integer v = entry.getValue();
            Map<String, Object> item = new HashMap<>();
            item.put("assetName", k);
            item.put("assetQuantity", v);
            items.add(item);

        }

        OrgAssetQuantityTable.getItems().setAll(items);

        List<Order> buyOrders = MockSocket.getInstance().GetOrganisationBuyOrders(LoginController.GetToken(), "Sales");
        OrgBuyOrdersTable.getItems().setAll(buyOrders);

        List<Order> sellOrders = MockSocket.getInstance().GetOrganisationSellOrders(LoginController.GetToken(), "Sales");
        OrgSellOrdersTable.getItems().setAll(sellOrders);
    }

    private void updatePage() {
        long endTime = 2000;
        DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss" );
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {
                            UpdateOrganisationInformation();
                            System.out.println("Updating");
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
    }
}
