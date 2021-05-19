package Controllers.FrontEnd.User;

import Controllers.Backend.NetworkObjects.Order;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.Backend.Socket.MockSocket;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Handles User remove tab
 */
public class UserRemoveTabController implements Initializable, Observer {

    @FXML
    Label RemoveOrganisationName;

    @FXML
    TableView RemoveTable;

    @FXML
    TableColumn<Order, String> RemoveAssetTypeColumn;
    @FXML
    TableColumn<Order, String> RemoveQuantityColumn;
    @FXML
    TableColumn<Order, String> RemovePriceColumn;
    @FXML
    TableColumn<Order, String> RemoveDateColumn;
    @FXML
    TableColumn<Order, Void> RemoveButtonColumn;


    Button[] RemoveButtons;

    /**
     * Initialise User Remove Handler
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RemoveOrganisationName.setText("Sales" + " Orders ");
        RemoveAssetTypeColumn.setCellValueFactory(new PropertyValueFactory<>("assetType"));
        RemoveQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("assetQuantity"));
        RemovePriceColumn.setCellValueFactory(new PropertyValueFactory<>("requestPrice"));
        RemoveDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        UpdateRemoveTable();
        addButtonToTable();
    }

    /**
     * Remove order from system
     * @param RemoveOrder - order to remove
     */
    public void RemoveOrder(ActionEvent RemoveOrder) {
        System.out.println(RemoveOrder.getSource());
    }

    /**
     * Update remove table
     */
    private void UpdateRemoveTable() {
        List<Order> buyOrders = MockSocket.getInstance().GetOrganisationOrders(LoginController.GetToken(), "Sales");
        RemoveTable.getItems().setAll(buyOrders);
    }

    /**
     * Update organisational unit text field.
     */
    private void UpdateOrganisationText() {

    }

    //https://riptutorial.com/javafx/example/27946/add-button-to-tableview
    private void addButtonToTable() {

        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                final TableCell<Order, Void> cell = new TableCell<Order, Void>() {

                    private final Button btn = new Button("Remove");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Order order = getTableView().getItems().get(getIndex());
                            System.out.println("Remove: " + order);
                            MockSocket.getInstance().RemoveOrder(LoginController.GetToken(), order.getOrderID());
                            UpdateRemoveTable();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        RemoveButtonColumn.setCellFactory(cellFactory);
    }

    @Override
    public void update(Subject s) {

    }
}
