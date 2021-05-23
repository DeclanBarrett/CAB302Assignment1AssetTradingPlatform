package Controllers.FrontEnd.User;

import Controllers.BackEnd.NetworkObjects.Order;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.BackEnd.Socket.MockSocket;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    Label userRemoveErrorText;

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

    /**
     * Initialise User Remove Handler
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateOrganisationText();
        RemoveAssetTypeColumn.setCellValueFactory(new PropertyValueFactory<>("assetType"));
        RemoveQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("assetQuantity"));
        RemovePriceColumn.setCellValueFactory(new PropertyValueFactory<>("requestPrice"));
        RemoveDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        UpdateRemoveTable();
        addButtonToTable();
    }

    /**
     * Remove order from system
     */
    public void RemoveOrder(Order order) {

        String clientResponse = "";

        try {
            clientResponse = MockSocket.getInstance().RemoveOrder(LoginController.GetToken(), order.getOrderID());
            UpdateRemoveTable();
        } catch (Exception e) {
            clientResponse = e.getMessage();
            userRemoveErrorText.setTextFill(Color.RED);
        }

        userRemoveErrorText.setText(clientResponse);
    }

    /**
     * Update remove table
     */
    private void UpdateRemoveTable() {

        List<Order> buyOrders = new ArrayList<>();

        String clientResponse = userRemoveErrorText.getText();;

        try {
            buyOrders = MockSocket.getInstance().GetOrganisationOrders(LoginController.GetToken(), LoginController.GetUser().getOrganisationalUnit());
            userRemoveErrorText.setTextFill(Color.GREEN);
        } catch (Exception e) {
            userRemoveErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        userRemoveErrorText.setText(clientResponse);
        RemoveTable.getItems().setAll(buyOrders);
    }

    /**
     * Update organisational unit text field.
     */
    private void updateOrganisationText() {
        try {
            RemoveOrganisationName.setText(LoginController.GetUser().getOrganisationalUnit() + " Orders ");
        } catch (Exception e) {
            RemoveOrganisationName.setText("NOT LOGGED IN");
        }
    }

    //https://riptutorial.com/javafx/example/27946/add-button-to-tableview

    /**
     * Adds a remove button to the table in the row
     */
    private void addButtonToTable() {

        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                final TableCell<Order, Void> cell = new TableCell<Order, Void>() {

                    private final Button btn = new Button("Remove");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            RemoveOrder(getTableView().getItems().get(getIndex()));
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
        UpdateRemoveTable();
    }
}
