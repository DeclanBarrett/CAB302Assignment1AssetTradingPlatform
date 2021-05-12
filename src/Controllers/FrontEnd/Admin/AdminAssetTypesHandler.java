package Controllers.FrontEnd.Admin;

import Controllers.FrontEnd.LoginController;
import Controllers.Socket.MockSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    TableView CreateAssetTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateAssetTypes();
    }

    /**
     * Creates assets in database
     * @param CreateAsset Asset to be created
     */
    public void CreateAsset(ActionEvent CreateAsset) {
        MockSocket.getInstance().AddAsset(LoginController.GetToken(), CreateAssetName.getText());


        System.out.println(CreateAsset.getSource());
    }

    /**
     * Updates asset types
     */
    private void UpdateAssetTypes() {
        System.out.println("Updating Asset Types");
        List<String> types = MockSocket.getInstance().GetAssetTypes(LoginController.GetToken());
        CreateAssetTable.getItems().addAll(types);

    }
}
