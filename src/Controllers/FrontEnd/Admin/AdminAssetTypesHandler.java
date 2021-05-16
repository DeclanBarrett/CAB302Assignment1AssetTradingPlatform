package Controllers.FrontEnd.Admin;

import Controllers.FrontEnd.Login.LoginController;
import Controllers.Backend.Socket.MockSocket;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Handles events in admin asset types tab.
 */
public class AdminAssetTypesHandler implements Initializable {

    @FXML
    TextField CreateAssetName;
    @FXML
    Label CreateAssetErrorText;
    @FXML
    Button CreateAssetSubmit;
    @FXML
    ListView<String> CreateAssetTable;
    //@FXML
    //TableColumn<String, String> AssetTypeColumn;


    private ObservableList<String> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //AssetTypeColumn.setCellValueFactory(new PropertyValueFactory<>("assetType"));
        //CreateAssetTable.getColumns().add(AssetTypeColumn);

        //data = FXCollections.observableArrayList();

        //TableColumn<Person, String> column1 = new TableColumn<>("First Name");
        //column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//
//
        //TableColumn<Person, String> column2 = new TableColumn<>("Last Name");
        //column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//
//
        //tableView.getColumns().add(column1);
        //tableView.getColumns().add(column2);




        UpdateAssetTypes();
    }

    /**
     * Creates assets in database
     * @param CreateAsset Asset to be created
     */
    public void CreateAsset(ActionEvent CreateAsset) {
        MockSocket.getInstance().AddAsset(LoginController.GetToken(), CreateAssetName.getText());


        System.out.println(CreateAsset.getSource());
        UpdateAssetTypes();
    }

    /**
     * Updates asset types
     */
    private void UpdateAssetTypes() {

        System.out.println("Updating Asset Types");

        List<String> types = MockSocket.getInstance().GetAssetTypes(LoginController.GetToken());

        CreateAssetTable.getItems().setAll(types);
        //Fill the list view with the retrieved types
        /*
        for (String type : types) {
            CreateAssetTable.getItems().add(type);
            //data.add(type);
            System.out.println(type);
        }
        */



    }
}
